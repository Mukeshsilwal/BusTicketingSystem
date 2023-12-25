package com.Transaction.transaction.service;

import com.Transaction.transaction.entity.Booking;
import com.Transaction.transaction.entity.Seat;

import java.util.HashMap;
import java.util.Map;

public class SeatAllocationService {
    private Map<String, Seat> seatMap = new HashMap<>();
    private Map<String, Booking> bookingMap = new HashMap<>();

    public SeatAllocationService() {
        // Initialize seats
        for (int i = 1; i <= 50; i++) {
            Seat seat = new Seat("Seat" + i);
            seatMap.put(seat.getSeatNumber(), seat);
        }
    }

    public Seat allocateSeat(String customerId) {
        // Randomly allocate a seat
        Seat seat = getRandomAvailableSeat();
        if (seat != null) {
            seat.setReserved(true);
            Booking booking = new Booking(customerId, seat);
            bookingMap.put(customerId, booking);
        }
        return seat;
    }

    public boolean cancelBooking(String customerId) {
        Booking booking = bookingMap.get(customerId);
        if (booking != null) {
            Seat seat = booking.getSeats();
            seat.setReserved(false);
            bookingMap.remove(customerId);
            return true; // Booking canceled successfully
        }
        return false; // Booking not found
    }

    private Seat getRandomAvailableSeat() {
        // Implement logic to get a random available seat
        // For simplicity, let's assume the first available seat is chosen
        for (Seat seat : seatMap.values()) {
            if (!seat.isReserved()) {
                return seat;
            }
        }
        return null; // No available seats
    }
}
