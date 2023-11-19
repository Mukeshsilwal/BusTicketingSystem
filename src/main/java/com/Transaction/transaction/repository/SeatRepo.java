package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BookingTicket;
import com.Transaction.transaction.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat,Integer> {
    Seat findBySeatNameContaining(String seatName);
}
