package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRequestRepo extends JpaRepository<BookingRequest,Integer> {
}
