package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.algorithm.DynamicPricingAlgorithm;
import com.Transaction.transaction.entity.AvailableSeat;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.ResourceNotFound;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.AvailableDto;
import com.Transaction.transaction.repository.AvailableSeatRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.PricingService;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {
    public final DynamicPricingAlgorithm algorithm;
    private final AvailableSeatRepo availableSeatRepo;
    private final ModelMapper mapper;
    private final SeatRepo seatRepo;
    private SeatService seatService;

    @Override
    public AvailableDto createAvailableSeats(AvailableDto availableDto) {
        AvailableSeat  seat=this.toAvailableSeat(availableDto);
        AvailableSeat availableSeat=this.availableSeatRepo.save(seat);
        return  toDto(availableSeat);
    }

    @Override
    public double calculateDemandFactor(int seatId) {
        Seat seat = seatRepo.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat","seatId",seatId));
        if (!seat.isReserved() && seat.getBusInfo() != null) {
            int totalAvailableSeats = 30;
            System.out.println("Seats :"+totalAvailableSeats);
            LocalDateTime departureTime = seat.getBusInfo().getDepartureDateTime();
            return algorithm.calculateDynamicPrice(departureTime, totalAvailableSeats);
        } else {
            // Default demand factor if the seat is already allocated or bus info is missing
            return 1.0;
        }
    }
    public int getAvailableSeatsForBus(int id) {
        // Assume seatRepository has a method to get seat information by busId
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
        if (seat != null) {
            // Calculate available seats based on your business logic
            return calculateAvailableSeats(seat.getCapacity(), seat.getBooking().getNoOfSeats());
        } else {
            // Handle case where the bus is not found
            throw new ResourceNotFound("Bus not found with id: ");
        }
    }
    // Calculate available seats based on the total seats and booked seats
    private int calculateAvailableSeats(int totalSeats, int bookedSeats) {
        // Your business logic to calculate available seats
        return totalSeats - bookedSeats;
    }

    @Override
    public AvailableDto updateSeat(AvailableDto availableDto,int id) {
        AvailableSeat seat=this.availableSeatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Available","id",id));
        seat.setAvailableSeats(availableDto.getAvailableSeats());
        AvailableSeat availableSeat=this.availableSeatRepo.save(seat);
        return toDto(availableSeat);
    }

    public AvailableSeat toAvailableSeat(AvailableDto availableDto){
        return this.mapper.map(availableDto,AvailableSeat.class);
    }
    public AvailableDto toDto(AvailableSeat availableSeat){
        return this.mapper.map(availableSeat,AvailableDto.class);
    }
}
