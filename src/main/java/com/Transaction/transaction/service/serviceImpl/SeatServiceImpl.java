package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.algorithm.DynamicPricingAlgorithm;
import com.Transaction.transaction.entity.Bus;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.exception.SeatsNotAvailableException;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.repository.BusRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepo seatRepo;
    private final ModelMapper modelMapper;
    private final BusRepo busRepo;
    private final DynamicPricingAlgorithm algorithm;


    @Override
    public SeatDto updateSeat(SeatDto seatDto, int id) {
        Seat seat = this.seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
        seat.setSeatNumber(seatDto.getSeatNumber());
        Seat seat1 = this.seatRepo.save(seat);
        return seatToDto(seat1);
    }

    @Override
    public void deleteSeat(int id) {
        Seat seat = this.seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
        Bus busInfo = seat.getBus();
        if (busInfo != null) {
            busInfo.getSeats().remove(seat);
            busRepo.save(busInfo);
        }
        this.seatRepo.delete(seat);
    }

    @Override
    public SeatDto getSeatById(int id) {
        Seat seat = this.seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
        return seatToDto(seat);
    }

    @Override
    public List<SeatDto> getAllSeat() {
        List<Seat> seats = this.seatRepo.findAll();
        return seats.stream().map(this::seatToDto).collect(Collectors.toList());
    }

    @Override
    public SeatDto createSeatForBus(SeatDto seatDto, int id) {
        Seat seat = this.dtoToSeat(seatDto);
        Bus busInfo = this.busRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusInfo", "id", id));
        if (!seat.isReserved() && busInfo != null) {
            int availableSeats = calculateAvailableSeats(busInfo);
            System.out.println("available seats :" + availableSeats);
            BigDecimal price = algorithm.calculateDynamicPrice(availableSeats, busInfo.getDate(), busInfo);
            seat.setPrice(price);
        } else {
            throw new SeatsNotAvailableException("Seat not available :");
        }
        seat.setBus(busInfo);
        Seat seat1 = this.seatRepo.save(seat);
        return seatToDto(seat1);
    }

    private int calculateAvailableSeats(Bus busInfo) {
        List<Seat> reservedSeats = seatRepo.findByBusAndReserved(busInfo, true);
        int totalSeats = seatRepo.countByBus(busInfo);
        System.out.println("Total Seats :" + totalSeats);
        return totalSeats - reservedSeats.size();
    }

    @Override
    public List<SeatDto> findSeatRelatedToBus(String busName) {
        List<Seat> seats = seatRepo.findByBusBusName(busName);
        return seats.stream().map(this::seatToDto).collect(Collectors.toList());
    }


    public Seat dtoToSeat(SeatDto seatDto) {
        return this.modelMapper.map(seatDto, Seat.class);
    }

    public SeatDto seatToDto(Seat seat) {
        return this.modelMapper.map(seat, SeatDto.class);
    }

    public List<SeatDto> toDto(List<Seat> seats) {
        return seats.stream()
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .collect(Collectors.toList());
    }

    public List<Seat> toSeat(List<SeatDto> seatDtos) {
        return seatDtos.stream()
                .map(seatDto -> {
                    Seat seat = modelMapper.map(seatDto, Seat.class);
                    System.out.println("Mapped SeatDto to Seat: " + seat);
                    return seat;
                })
                .collect(Collectors.toList());
    }


}
