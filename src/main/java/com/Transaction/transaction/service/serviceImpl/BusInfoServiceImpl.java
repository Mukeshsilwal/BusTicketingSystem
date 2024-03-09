package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.algorithm.DynamicPricingAlgorithm;
import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.exception.SeatsNotAvailableException;
import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.payloads.Route12Dto;
import com.Transaction.transaction.repository.BusInfoRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.BusInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusInfoServiceImpl implements BusInfoService {
    private final BusInfoRepo busInfoRepo;
    private final ModelMapper modelMapper;
    private final RouteRepo routeRepo;
    private final SeatRepo seatRepo;
    private final DynamicPricingAlgorithm algorithm;

    public BusInfoServiceImpl(BusInfoRepo busInfoRepo, ModelMapper modelMapper, RouteRepo routeRepo, SeatRepo seatRepo, DynamicPricingAlgorithm algorithm) {
        this.busInfoRepo = busInfoRepo;
        this.modelMapper = modelMapper;
        this.routeRepo = routeRepo;
        this.seatRepo = seatRepo;
        this.algorithm = algorithm;
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
    public BusInfoDto createBusForRoute(BusInfoDto busInfoDto, int id,int busId) {
        BusInfo busInfo=this.dtoToBusInfo(busInfoDto);
        Route12 route12=this.routeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route12","routeIs",id));
        BusInfo busInfo1 = this.busInfoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusInfo", "id", id));
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
        if(!seat.isReserved()&&busInfo1!=null) {
            int availableSeats = calculateAvailableSeats(busInfo1);
            System.out.println("available seat" + availableSeats);
            double price = algorithm.calculateDynamicPrice(busInfo1.getDepartureDateTime(), availableSeats);
            busInfo.setPrice(price);
        }
        else{
            throw new SeatsNotAvailableException("Seat not available :");
        }
        busInfo.setRoute12(route12);
        BusInfo busInfo2=this.busInfoRepo.save(busInfo);
        return busInfoToDto(busInfo2);
    }
    private int calculateAvailableSeats(BusInfo busInfo) {
        // Assuming you have a SeatRepository to query the database
        List<Seat> reservedSeats = seatRepo.findByBusInfoAndReserved(busInfo, true);

        // Assuming a bus with 50 seats
        int totalSeats = 33;

        // Calculate the available seats by subtracting the reserved seats from the total seats
        return totalSeats - reservedSeats.size();
    }

    @Override
    public List<BusInfoDto> getAllBusInfo() {
        List<BusInfo> busInfos=this.busInfoRepo.findAll();
        return busInfos.stream().map(this::busInfoToDto).collect(Collectors.toList());
    }

    @Override
    public List<BusInfoDto> getBusByRoute(String source, String destination, Date date) {
        List<BusInfo> busInfos=this.busInfoRepo.findByRoute12SourceBusStopNameAndRoute12DestinationBusStopNameAndDepartureDateTime(source,destination,date);
        return busInfos.stream().map(this::busInfoToDto).collect(Collectors.toList());
    }


    public BusInfo dtoToBusInfo(BusInfoDto busInfoDto){
        return this.modelMapper.map(busInfoDto, BusInfo.class);
    }
    public BusInfoDto busInfoToDto(BusInfo busInfo){
        return this.modelMapper.map(busInfo,BusInfoDto.class);
    }
    public Route12 dtoToRoute (Route12Dto route12Dto){
        return this.modelMapper.map(route12Dto, Route12.class);
    }
}
