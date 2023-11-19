package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.repository.BusInfoRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.BusInfoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


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
    public BusInfoDto createBusInfo(BusInfoDto busInfoDto) {
        BusInfo busInfo=this.dtoToBusInfo(busInfoDto);
        BusInfo busInfo1=this.busInfoRepo.save(busInfo);
        return busInfoToDto(busInfo1);
    }

    @Override
    public BusInfoDto updateBusInfo(BusInfoDto busInfoDto, int id,int routeId) {
        BusInfo busInfo=this.busInfoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("BusInfo","id",id));
        Route12 route12=this.routeRepo.findById(routeId).orElseThrow(()->new ResourceNotFoundException("Route12","routeIs",routeId));
        busInfo.setBusName(busInfoDto.getBusName());
        busInfo.setBusType(busInfoDto.getBusType());
        busInfo.setRoute12(route12);
        busInfo.setPrice(busInfoDto.getPrice());
        busInfo.setTime(busInfoDto.getTime());
        BusInfo busInfo1=this.busInfoRepo.save(busInfo);
        return busInfoToDto(busInfo1);
    }

    @Override
    public void deleteBusInfo(Integer id) {
        BusInfo busInfo=this.busInfoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("BusInfo","id",id));
        this.busInfoRepo.delete(busInfo);
    }

    @Override
    public BusInfoDto createBusForRoute(BusInfoDto busInfoDto, int id) {
        BusInfo busInfo=this.dtoToBusInfo(busInfoDto);
        Route12 route12=this.routeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route12","routeIs",id));
        busInfo.setRoute12(route12);
        BusInfo busInfo1=this.busInfoRepo.save(busInfo);
        return busInfoToDto(busInfo1);
    }

    @Override
    public void deleteBusInfoWithRoute(int id, int routeId) {
        BusInfo busInfo=this.busInfoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("BusInfo","id",id));
        Route12 route12=this.routeRepo.findById(routeId).orElseThrow(()->new ResourceNotFoundException("Route12","routeIs",routeId));
        this.busInfoRepo.delete(busInfo);
        this.routeRepo.delete(route12);

    }

    @Override
    public List<BusInfoDto> getAllBusInfo() {
        List<BusInfo> busInfos=this.busInfoRepo.findAll();
        List<BusInfoDto> busInfoDtos=busInfos.stream().map((bus)->this.busInfoToDto(bus)).collect(Collectors.toList());
        return busInfoDtos;
    }

    public BusInfo dtoToBusInfo(BusInfoDto busInfoDto){
        BusInfo busInfo=this.modelMapper.map(busInfoDto, BusInfo.class);
        return busInfo;
    }
    public BusInfoDto busInfoToDto(BusInfo busInfo){
        BusInfoDto busInfoDto=this.modelMapper.map(busInfo,BusInfoDto.class);
        return busInfoDto;
    }
}
