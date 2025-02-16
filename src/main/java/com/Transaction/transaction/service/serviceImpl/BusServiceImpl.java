package com.Transaction.transaction.service.serviceImpl;


import com.Transaction.transaction.entity.Bus;
import com.Transaction.transaction.entity.Route;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.BusDto;
import com.Transaction.transaction.repository.BusRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.BusService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {
    private final BusRepo busRepo;
    private final ModelMapper modelMapper;
    private final RouteRepo routeRepo;

    public BusServiceImpl(BusRepo busRepo, ModelMapper modelMapper, RouteRepo routeRepo) {
        this.busRepo = busRepo;
        this.modelMapper = modelMapper;
        this.routeRepo = routeRepo;
    }

    @Override
    public BusDto updateBusInfo(BusDto busDto, int id, int routeId) {
        Bus busInfo = this.busRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusInfo", "id", id));
        Route route12 = this.routeRepo.findById(routeId).orElseThrow(() -> new ResourceNotFoundException("Route12", "routeIs", routeId));
        busInfo.setBusName(busDto.getBusName());
        busInfo.setBusType(busDto.getBusType());
        busInfo.setRoute(route12);
        Bus busInfo1 = this.busRepo.save(busInfo);
        return busInfoToDto(busInfo1);
    }

    @Override
    public void deleteBusInfo(Integer id) {
        Bus busInfo = this.busRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusInfo", "id", id));
        Route route12 = busInfo.getRoute();
        if (route12 != null) {
            route12.getBusInfos().remove(busInfo);
            routeRepo.save(route12);
        }
        this.busRepo.delete(busInfo);
    }

    @Override
    public BusDto createBusForRoute(BusDto busDto, int id) {
        Bus busInfo = this.dtoToBusInfo(busDto);
        Route route12 = this.routeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Route12", "routeIs", id));
        busInfo.setRoute(route12);
        Bus busInfo2 = this.busRepo.save(busInfo);
        return busInfoToDto(busInfo2);
    }

    @Override
    public List<BusDto> getAllBusInfo() {
        List<Bus> busInfos = this.busRepo.findAll();
        return busInfos.stream().map(this::busInfoToDto).collect(Collectors.toList());
    }

    @Override
    public List<BusDto> getBusByRoute(String source, String destination, LocalDate time) {
        List<Bus> busInfos = this.busRepo.findByRouteSourceBusStopNameAndRouteDestinationBusStopNameAndDate(source, destination, time);
        return busInfos.stream().map(this::busInfoToDto).collect(Collectors.toList());
    }

    public Bus dtoToBusInfo(BusDto busDto) {
        return this.modelMapper.map(busDto, Bus.class);
    }

    public BusDto busInfoToDto(Bus busInfo) {
        return this.modelMapper.map(busInfo, BusDto.class);
    }
}
