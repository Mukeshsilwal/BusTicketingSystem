package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.config.BusConfig;
import com.Transaction.transaction.entity.BusStop;
import com.Transaction.transaction.entity.BusStopDistance;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.model.Graph;
import com.Transaction.transaction.payloads.Route12Dto;
import com.Transaction.transaction.repository.BusStopDistanceRepo;
import com.Transaction.transaction.repository.BusStopRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.Route12Service;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements Route12Service {
    private final RouteRepo routeRepo;
    private final ModelMapper modelMapper;
   private final BusConfig busConfig;
   private final BusStopRepo busStopRepo;
   private final BusStopDistanceRepo busStopDistanceRepo;

    public RouteServiceImpl(RouteRepo routeRepo, ModelMapper modelMapper, BusConfig busConfig, BusStopRepo busStopRepo, BusStopDistanceRepo busStopDistanceRepo) {
        this.routeRepo = routeRepo;
        this.modelMapper = modelMapper;
        this.busConfig = busConfig;
        this.busStopRepo = busStopRepo;
        this.busStopDistanceRepo = busStopDistanceRepo;
    }
    @Override
    public Route12Dto createRoute(Route12Dto route12Dto) {
        Route12 route12=this.dtoToRoute(route12Dto);
        Route12 route121=this.routeRepo.save(route12);
        return routeToDto(route121);
    }

    @Override
    public Route12Dto updateRoute(Route12Dto route12Dto, int id) {
        Route12 route12=this.routeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route12","id",id));
        route12.setDate(route12Dto.getDate());
        Route12  route121=this.routeRepo.save(route12);
        return routeToDto(route121);
    }

    @Override
    public void deleteRoute(int id) {
        Route12 route12=this.routeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route12","id",id));
        this.routeRepo.delete(route12);
    }

    @Override
    public Route12Dto getRouteById(int id) {
        Route12 route12=this.routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Route12","id",id));
        return routeToDto(route12);
    }

    @Override
    public List<Route12Dto> getAllRoute() {
        List<Route12> route12s=this.routeRepo.findAll();
        return route12s.stream().map(this::routeToDto).collect(Collectors.toList());
    }

    @Override
    public Route12Dto createRouteWithBusStop(Route12Dto route12Dto, int id, int id1) {
        BusStop busStop = this.busStopRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusStop", "id", id));
        BusStop busStop1 = this.busStopRepo.findById(id1).orElseThrow(() -> new ResourceNotFoundException("BusStop", "id1", id1));
        List<BusStop> busStops = this.busStopRepo.findAll();
        Route12 route12 = this.dtoToRoute(route12Dto);
        route12.setSourceBusStop(busStop);
        route12.setDestinationBusStop(busStop1);
        List<Route12> route12s = new ArrayList<>();
        route12s.add(route12);
        List<BusStop> busStops1 = new ArrayList<>(busStops);
        Route12 route121 = this.routeRepo.save(route12);
        Graph graph = new Graph();
        graph.setRoute12s(route12s);
        graph.setBusStops(busStops1);
        busConfig.dijkstra(graph,busStop);

        for (BusStop b : busStops) {
            BusStopDistance busStopDistance = new BusStopDistance();
            busStopDistance.setSourceBusStop(busStop);
            busStopDistance.setDestinationBusStop(b);
            busStopDistance.setDistance(b.getDistance());
            busStopDistanceRepo.save(busStopDistance);
        }

        return routeToDto(route121);
    }


    public Route12 dtoToRoute(Route12Dto route12Dto){
        return this.modelMapper.map(route12Dto, Route12.class);
    }
    public Route12Dto routeToDto(Route12 route12){
        return this.modelMapper.map(route12, Route12Dto.class);
    }
}
