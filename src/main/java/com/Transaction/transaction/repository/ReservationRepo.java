package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
}
