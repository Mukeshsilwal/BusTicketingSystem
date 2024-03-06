package com.Transaction.transaction.algorithm;

import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.ResourceNotFound;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
public class SeatAllocationService {

    public List<Seat> allocateRandomSeats(List<Seat> seats, int numberOfSeatsToAllocate) {
        // Filter available seats
        List<Seat> availableSeats = seats.stream()
                .filter(seat -> !seat.isReserved())
                .collect(Collectors.toList());

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

        return allocatedSeats;
    }

    private List<Seat> shuffleSeats(List<Seat> seats) {
        List<Seat> shuffledSeats = List.copyOf(seats);
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
}
