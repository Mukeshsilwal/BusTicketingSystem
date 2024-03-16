package com.Transaction.transaction.service.serviceImpl;


import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.repository.BusInfoRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.BusInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusInfoServiceImpl implements BusInfoService {
    private final BusInfoRepo busInfoRepo;
    private final ModelMapper modelMapper;
    private final RouteRepo routeRepo;
    public BusInfoServiceImpl(BusInfoRepo busInfoRepo, ModelMapper modelMapper, RouteRepo routeRepo) {
        this.busInfoRepo = busInfoRepo;
        this.modelMapper = modelMapper;
        this.routeRepo = routeRepo;
    }
    @Override
    public BusInfoDto updateBusInfo(BusInfoDto busInfoDto, int id,int routeId) {
        BusInfo busInfo=this.busInfoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("BusInfo","id",id));
        Route12 route12=this.routeRepo.findById(routeId).orElseThrow(()->new ResourceNotFoundException("Route12","routeIs",routeId));
        busInfo.setBusName(busInfoDto.getBusName());
        busInfo.setBusType(busInfoDto.getBusType());
        busInfo.setRoute12(route12);
        BusInfo busInfo1=this.busInfoRepo.save(busInfo);
        return busInfoToDto(busInfo1);
    }
    @Override
    public void deleteBusInfo(Integer id) {
        BusInfo busInfo=this.busInfoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("BusInfo","id",id));
        Route12 route12=busInfo.getRoute12();
        if (route12 != null) {
            route12.getBusInfos().remove(busInfo);
            // Optionally, update the BusInfo if needed
            routeRepo.save(route12);
        }
        this.busInfoRepo.delete(busInfo);
    }
    @Override
    public BusInfoDto createBusForRoute(BusInfoDto busInfoDto, int id) {
        BusInfo busInfo=this.dtoToBusInfo(busInfoDto);
        Route12 route12=this.routeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route12","routeIs",id));
        busInfo.setRoute12(route12);
        BusInfo busInfo2=this.busInfoRepo.save(busInfo);
        return busInfoToDto(busInfo2);
    }
    @Override
    public List<BusInfoDto> getAllBusInfo() {
        List<BusInfo> busInfos=this.busInfoRepo.findAll();
        return busInfos.stream().map(this::busInfoToDto).collect(Collectors.toList());
    }
    @Override
    public List<BusInfoDto> getBusByRoute(String source, String destination, LocalDate time) {
        List<BusInfo> busInfos=this.busInfoRepo.findByRoute12SourceBusStopNameAndRoute12DestinationBusStopNameAndDate(source,destination,time);
        return busInfos.stream().map(this::busInfoToDto).collect(Collectors.toList());
    }
    public BusInfo dtoToBusInfo(BusInfoDto busInfoDto){
        return this.modelMapper.map(busInfoDto, BusInfo.class);
    }
    public BusInfoDto busInfoToDto(BusInfo busInfo){
        return this.modelMapper.map(busInfo,BusInfoDto.class);
    }
}
