package com.Transaction.transaction.algorithm;

import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.SeatAlreadyReserved;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatAllocationService {
    private final List<Seat> availableSeats = new ArrayList<>();

    // Initialize seats with zones
    public SeatAllocationService() {
        for (int i = 1; i <= 11; i++) {
            availableSeats.add(new Seat("Seat" + i, "FrontZone"));
        }
        for (int i = 11; i <= 22; i++) {
            availableSeats.add(new Seat("Seat" + i, "MidZone"));
        }
        for (int i = 21; i <= 33; i++) {
            availableSeats.add(new Seat("Seat" + i, "BackZone"));
        }
    }

    // Method to allocate a seat based on the chosen zone
    public Seat allocateSeat(String chosenZone) {
        for (Seat seat : availableSeats) {
            if (seat.getZone().equalsIgnoreCase(chosenZone)) {
                availableSeats.remove(seat);
                return seat;
            }
        }
        throw new SeatAlreadyReserved("No available seats in the chosen zone"); // No available seats in the chosen zone
    }

    // Method to check the availability of seats
    public boolean hasAvailableSeats() {
        return !availableSeats.isEmpty();
    }
}

