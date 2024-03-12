package com.Transaction.transaction.service.serviceImpl;



import com.Transaction.transaction.entity.BusStop;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.Route12Dto;
import com.Transaction.transaction.repository.BusStopRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.Route12Service;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class  RouteServiceImpl implements Route12Service {
    private final RouteRepo routeRepo;
    private final ModelMapper modelMapper;
   private final BusStopRepo busStopRepo;
    private static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);
    private static final int INFINITY = Integer.MAX_VALUE;

    public RouteServiceImpl(RouteRepo routeRepo, ModelMapper modelMapper, BusStopRepo busStopRepo) {
        this.routeRepo = routeRepo;
        this.modelMapper = modelMapper;
        this.busStopRepo = busStopRepo;

    }

    @Override
    public Route12Dto updateRoute(Route12Dto route12Dto, int id) {
        Route12 route12=this.routeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route12","id",id));
        Route12  route121=this.routeRepo.save(route12);
        return routeToDto(route121);
    }

    @Override
    public void deleteRoute(int id) {
        Route12 route12=this.routeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route12","id",id));
        BusStop busStop=route12.getDestinationBusStop();
        if(busStop!=null){
            busStop.getDestinationRoutes().remove(route12);
            busStopRepo.save(busStop);
        }
        BusStop busStop1=route12.getSourceBusStop();
        if(busStop1!=null){
            busStop1.getSourceRoutes().remove(route12);
            busStopRepo.save(busStop1);
        }
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
        Route12 route12 = this.dtoToRoute(route12Dto);
        route12.setSourceBusStop(busStop);
        route12.setDestinationBusStop(busStop1);
        Route12 route121 = this.routeRepo.save(route12);
        return routeToDto(route121);
    }
    public Route12 dtoToRoute (Route12Dto route12Dto){
            return this.modelMapper.map(route12Dto, Route12.class);
        }
        public Route12Dto routeToDto (Route12 route12){
            return this.modelMapper.map(route12, Route12Dto.class);
        }

}
