package com.Transaction.transaction.service.serviceImpl;


import com.Transaction.transaction.entity.BusStop;
import com.Transaction.transaction.entity.Route;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.RouteDto;
import com.Transaction.transaction.repository.BusStopRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.RouteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
    private final RouteRepo routeRepo;
    private final ModelMapper modelMapper;
    private final BusStopRepo busStopRepo;

    public RouteServiceImpl(RouteRepo routeRepo, ModelMapper modelMapper, BusStopRepo busStopRepo) {
        this.routeRepo = routeRepo;
        this.modelMapper = modelMapper;
        this.busStopRepo = busStopRepo;

    }

    @Override
    public RouteDto updateRoute(RouteDto routeDto, int id) {
        Route route12 = this.routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Route12", "id", id));
        Route route121 = this.routeRepo.save(route12);
        return routeToDto(route121);
    }

    @Override
    public void deleteRoute(int id) {
        Route route12 = this.routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Route12", "id", id));
        BusStop busStop = route12.getDestinationBusStop();
        if (busStop != null) {
            busStop.getDestinationRoutes().remove(route12);
            busStopRepo.save(busStop);
        }
        BusStop busStop1 = route12.getSourceBusStop();
        if (busStop1 != null) {
            busStop1.getSourceRoutes().remove(route12);
            busStopRepo.save(busStop1);
        }
        this.routeRepo.delete(route12);
    }

    @Override
    public RouteDto getRouteById(int id) {
        Route route12 = this.routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Route12", "id", id));
        return routeToDto(route12);
    }

    @Override
    public List<RouteDto> getAllRoute() {
        List<Route> route12s = this.routeRepo.findAll();
        return route12s.stream().map(this::routeToDto).collect(Collectors.toList());
    }

    @Override
    public RouteDto createRouteWithBusStop(RouteDto routeDto, int id, int id1) {
        BusStop busStop = this.busStopRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusStop", "id", id));
        BusStop busStop1 = this.busStopRepo.findById(id1).orElseThrow(() -> new ResourceNotFoundException("BusStop", "id1", id1));
        Route route12 = this.dtoToRoute(routeDto);
        route12.setSourceBusStop(busStop);
        route12.setDestinationBusStop(busStop1);
        Route route121 = this.routeRepo.save(route12);
        return routeToDto(route121);
    }

    public Route dtoToRoute(RouteDto routeDto) {
        return this.modelMapper.map(routeDto, Route.class);
    }

    public RouteDto routeToDto(Route route12) {
        return this.modelMapper.map(route12, RouteDto.class);
    }

}
