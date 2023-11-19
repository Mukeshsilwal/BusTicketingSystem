package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.BusStop;
import com.Transaction.transaction.entity.BusStopDistance;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.BusStopDistanceDto;
import com.Transaction.transaction.repository.BusStopDistanceRepo;
import com.Transaction.transaction.repository.BusStopRepo;
import com.Transaction.transaction.service.BusStopDistanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusStopDistanceServiceImpl implements BusStopDistanceService {
    private final BusStopDistanceRepo busStopDistanceRepo;
    private final BusStopRepo busStopRepo;
    private final ModelMapper modelMapper;
    @Override
    public BusStopDistanceDto createBusStopDistanceWithBusStop(BusStopDistanceDto busStopDistance, int id, int id1) {
        BusStop busStop=this.busStopRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusStop","id",id));
        BusStop busStop1=this.busStopRepo.findById(id1).orElseThrow(() -> new ResourceNotFoundException("BusStop","id1",id1));
        BusStopDistance busStopDistance1=this.dtoToStop(busStopDistance);
        busStopDistance1.setSourceBusStop(busStop);
        busStopDistance1.setDestinationBusStop(busStop1);
        BusStopDistance busStopDistance2=this.busStopDistanceRepo.save(busStopDistance1);
        return stopToDto(busStopDistance2);
    }

    @Override
    public List<BusStopDistanceDto> getAllBusStopDistance() {
        List<BusStopDistance> busStopDistances=this.busStopDistanceRepo.findAll();
        List<BusStopDistanceDto> busStopDistanceDtos=busStopDistances.stream().map(this::stopToDto).collect(Collectors.toList());
        return busStopDistanceDtos;
    }

    public BusStopDistance dtoToStop(BusStopDistanceDto busStopDistanceDto){
        return this.modelMapper.map(busStopDistanceDto, BusStopDistance.class);
    }
    public BusStopDistanceDto stopToDto(BusStopDistance busStopDistance){
        return this.modelMapper.map(busStopDistance,BusStopDistanceDto.class);
    }
}
