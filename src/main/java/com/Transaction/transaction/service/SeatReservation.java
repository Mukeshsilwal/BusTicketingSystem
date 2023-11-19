package com.Transaction.transaction.service;

import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.model.SeatType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatReservation {
    private List<Seat> availableSeat;
    private List<Seat> reservedSeat;

    public SeatReservation() {
        availableSeat = initializeAvailableSeat();
        reservedSeat = new ArrayList<>();
    }

    private List<Seat> initializeAvailableSeat() {
        List<Seat> seats = new ArrayList<>();

        // Create a list of available seats (for simplicity, let's consider rows A, B, C, and columns 1, 2, 3)
        for (char row1 = 'A'; row1 <= 'C'; row1++) {
            for (int col1 = 1; col1 <= 3; col1++) {
                seats.add(new Seat(row1, col1, SeatType.AISLE));
                seats.add(new Seat(row1, col1, SeatType.WINDOW));
                seats.add(new Seat(row1, col1, SeatType.MIDDEL));
            }
        }
        return seats;
    }

    public List<Seat> reserveSeats(int numSeats, SeatType preference) {
        List<Seat> selectedSeats = new ArrayList<>();

        if (numSeats <= 0) {
            return selectedSeats;
        }

        // Filter available seats based on preference
        List<Seat> availableSeatsByPreference = availableSeat.stream()
                .filter(seat1 -> seat1.getSeatType() == preference)
                .collect(Collectors.toList());

        if (availableSeatsByPreference.size() >= numSeats) {
            selectedSeats = availableSeatsByPreference.subList(0, numSeats);
        } else {
            // If there are not enough preferred seats, fill with available seats of any type
            selectedSeats = availableSeat.subList(0, numSeats);
        }
        return selectedSeats;
    }
}
