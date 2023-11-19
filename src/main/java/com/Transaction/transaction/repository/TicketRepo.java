package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket,Integer> {
}
