package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BookingTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<BookingTicket,Integer> {

}
