package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.BusStop;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.BusStopDto;
import com.Transaction.transaction.repository.BusStopRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.BusStopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusStopServiceImpl implements BusStopService {

    private final BusStopRepo busStopRepo;
    private final ModelMapper modelMapper;
    private final RouteRepo routeRepo;

    public BusStopServiceImpl(BusStopRepo busStopRepo, ModelMapper modelMapper, RouteRepo routeRepo) {
        this.busStopRepo = busStopRepo;
        this.modelMapper = modelMapper;
        this.routeRepo = routeRepo;
    }

    @Override
    public BusStopDto createBusStop(BusStopDto busStopDto) {
//        Route12 route12=this.routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Route12","id",id));
        BusStop busStop=this.dtoToBusStop(busStopDto);
//        List<Route12> route12s=new ArrayList<>();
//        route12s.add(route12);
//        busStop.setDestination(route12s);
        BusStop busStop1=this.busStopRepo.save(busStop);
        return busStopToDto(busStop1);
    }

    @Override
    public BusStopDto updateBusStop(BusStopDto busStopDto, int id) {
        BusStop busStop=this.dtoToBusStop(busStopDto);
        busStop.setDistance(busStopDto.getDistance());
        busStop.setName(busStopDto.getName());
        BusStop busStop1=this.busStopRepo.save(busStop);
        return busStopToDto(busStop1);
    }

    @Override
    public void deleteBusStop(int id) {
        BusStop busStop=this.busStopRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("BusStop","id",id));
        busStopRepo.delete(busStop);

    }

    @Override
    public List<BusStopDto> getAllBusStops() {
        List<BusStop> busStops=this.busStopRepo.findAll();
        return busStops.stream().map(this::busStopToDto).collect(Collectors.toList());
    }

    @Override
    public BusStopDto getBusStopById(int id) {
        BusStop busStop=this.busStopRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("BusStop","id",id));
        return busStopToDto(busStop);
    }
    public BusStop dtoToBusStop(BusStopDto busStopDto){
        return this.modelMapper.map(busStopDto, BusStop.class);
    }
    public BusStopDto busStopToDto(BusStop busStop){
        return this.modelMapper.map(busStop,BusStopDto.class);
    }
}
