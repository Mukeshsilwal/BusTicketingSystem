package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.algorithm.DynamicPricingAlgorithm;
import com.Transaction.transaction.algorithm.RandomSeatAllocator;
import com.Transaction.transaction.entity.AvailableSeat;
import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.ResourceNotFound;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.model.SeatType;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.repository.BusInfoRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.PricingService;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepo seatRepo;
    private final ModelMapper modelMapper;
    private final BusInfoRepo busInfoRepo;
    private final DynamicPricingAlgorithm algorithm;
    private final PricingService pricingService;



    @Override
    public SeatDto createSeat(SeatDto seatDto) {
        Seat seat=this.dtoToSeat(seatDto);
        Seat seat1=this.seatRepo.save(seat);
        return seatToDto(seat1);
    }

    @Override
    public SeatDto updateSeat(SeatDto seatDto, int id) {
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
            seat.setSeatName(seatDto.getSeatName());
            seat.setSeatNumber(seatDto.getSeatNumber());
            seat.setBusName(seatDto.getBusName());
            Seat seat1=this.seatRepo.save(seat);
            return seatToDto(seat1);
    }

    @Override
    public void deleteSeat(int id) {
        Seat seat = this.seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
        BusInfo busInfo = seat.getBusInfo();
        if (busInfo != null) {
            busInfo.getSeats().remove(seat);
            busInfoRepo.save(busInfo);
        }
        this.seatRepo.delete(seat);
    }

    @Override
    public SeatDto getSeatById(int id) {
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
        return seatToDto(seat);
    }

    @Override
    public List<SeatDto> getAllSeat() {
        List<Seat> seats=this.seatRepo.findAll();
        long count=seatRepo.count();
        System.out.println("Counted seat"+count);
        RandomSeatAllocator allocator=new RandomSeatAllocator((int) count);
        allocator.allocateSeat();
        for(int i=1;i<=count;i++){
            int allocatedSeat= allocator.allocateSeat();
            if(allocatedSeat!=-1){
                System.out.println("Passenger " + i + " allocated to Seat " + allocatedSeat);
            }
            else{
                System.out.println("No available seats for Passenger " + i);
            }
        }
        return seats.stream().map(this::seatToDto).collect(Collectors.toList());
    }


    @Override
    public SeatDto createSeatForBus(SeatDto seatDto, int id) {
        Seat seat = this.dtoToSeat(seatDto);
        BusInfo busInfo = this.busInfoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusInfo", "id", id));
        int count = 0;
        if (!seat.isReserved()) {
            count = (int) seatRepo.count();
        }
        double price = algorithm.calculateDynamicPrice(busInfo.getDepartureDateTime(), count);
        seat.setPrice(price);
        seat.setBusInfo(busInfo);
        Seat seat1 = this.seatRepo.save(seat);
        return seatToDto(seat1);
    }

    @Override
    public List<SeatDto> findSeatRelatedToBus(String busName) {
        List<Seat> seats=seatRepo.findByBusName(busName);
        return seats.stream().map(this::seatToDto).collect(Collectors.toList());
    }




    public Seat dtoToSeat(SeatDto seatDto){
        return this.modelMapper.map(seatDto,Seat.class);
    }
    public SeatDto seatToDto(Seat seat){
        return this.modelMapper.map(seat,SeatDto.class);
    }
}
