package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSeatsRepo extends JpaRepository<Booking,Integer> {
}
