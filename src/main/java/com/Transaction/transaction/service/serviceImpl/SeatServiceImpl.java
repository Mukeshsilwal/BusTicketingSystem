package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.algorithm.DynamicPricingAlgorithm;
import com.Transaction.transaction.algorithm.SeatAllocationService;
import com.Transaction.transaction.entity.AvailableSeat;
import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.ResourceNotFound;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.exception.SeatsNotAvailableException;
import com.Transaction.transaction.model.CustomerPreferences;
import com.Transaction.transaction.model.SeatType;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.repository.BusInfoRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.PricingService;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepo seatRepo;
    private final ModelMapper modelMapper;
    private final BusInfoRepo busInfoRepo;
    private final DynamicPricingAlgorithm algorithm;
    private final SeatAllocationService allocationService;



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
        List<Seat> seats = this.seatRepo.findAll();
       return seats.stream().map(this::seatToDto).collect(Collectors.toList());
    }



    @Override
    public SeatDto createSeatForBus(SeatDto seatDto, int id) {
        Seat seat = this.dtoToSeat(seatDto);
        BusInfo busInfo = this.busInfoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusInfo", "id", id));
        if(!seat.isReserved()&&busInfo!=null) {
            int availableSeats = calculateAvailableSeats(busInfo);
            System.out.println("available seat" + availableSeats);
            double price = algorithm.calculateDynamicPrice(busInfo.getDepartureDateTime(), availableSeats);
            seat.setPrice(price);
        }
        else{
            throw new  SeatsNotAvailableException("Seat not available :");
        }
        seat.setBusInfo(busInfo);
        Seat seat1 = this.seatRepo.save(seat);
        return seatToDto(seat1);
    }

    @Override
    public List<SeatDto> findSeatRelatedToBus(String busName) {
        List<Seat> seats=seatRepo.findByBusInfoBusName(busName);
        return seats.stream().map(this::seatToDto).collect(Collectors.toList());
    }

    @Override
    public List<SeatDto> allocateSeatsWithPreferences(List<SeatDto> seats, int numberOfSeatsToAllocate, CustomerPreferences preferences) {
        List<Seat> seats1=this.toSeat(seats);
        List<Seat> availableSeats = filterSeatsByPreferences(seats1, preferences);

        if (availableSeats.size() < numberOfSeatsToAllocate) {
            throw new ResourceNotFound("Not enough available seats to allocate.");
        }

        // Shuffle the available seats randomly
        List<Seat> shuffledSeats = shuffleSeats(availableSeats);

        // Allocate the requested number of seats
        List<Seat> allocatedSeats = new ArrayList<>();
        for (int i = 0; i < numberOfSeatsToAllocate; i++) {
            Seat allocatedSeat = shuffledSeats.get(i);

            // Check if the seat is reserved before allocating
            if (!allocatedSeat.isReserved()) {
                allocatedSeat.setReserved(true);  // Mark the seat as reserved
                allocatedSeats.add(allocatedSeat);
                // Perform any other necessary operations
            } else {
                // Handle the case when the seat is already reserved
                throw new ResourceNotFound("Seat is already reserved.");
            }
        }
        seatRepo.saveAll(allocatedSeats);
        return toDto(allocatedSeats);
    }
    private List<Seat> filterSeatsByPreferences(List<Seat> seats, CustomerPreferences preferences) {
        // Implement logic to filter seats based on customer preferences
        // For example, filter by seat type, location, etc.
        return seats.stream()
                .filter(seat -> !seat.isReserved())
                .filter(seat -> !preferences.isWindowSeatPreferred() || seat.getSeatType() == SeatType.WINDOW)
                .filter(seat -> !preferences.isMiddleSeatPreferred() || seat.getSeatType() == SeatType.MIDDLE)
                .filter(seat -> !preferences.isAisleSeatPreferred() || seat.getSeatType() == SeatType.AISLE)
                .collect(Collectors.toList());
    }
    private List<Seat> shuffleSeats(List<Seat> seats) {
        List<Seat> shuffledSeats = new ArrayList<>(seats);
        Random rand = new Random();

        for (int i = shuffledSeats.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            // Swap seats
            Seat temp = shuffledSeats.get(i);
            shuffledSeats.set(i, shuffledSeats.get(j));
            shuffledSeats.set(j, temp);
        }

        return shuffledSeats;
    }



    private int calculateAvailableSeats(BusInfo busInfo) {
        // Assuming you have a SeatRepository to query the database
        List<Seat> reservedSeats = seatRepo.findByBusInfoAndReserved(busInfo, true);

        // Assuming a bus with 50 seats
        int totalSeats = 33;

        // Calculate the available seats by subtracting the reserved seats from the total seats
        return totalSeats - reservedSeats.size();
    }



    public Seat dtoToSeat(SeatDto seatDto){
        return this.modelMapper.map(seatDto,Seat.class);
    }
    public SeatDto seatToDto(Seat seat){
        return this.modelMapper.map(seat,SeatDto.class);
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
