package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.AvailableSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableSeatRepo extends JpaRepository<AvailableSeat,Integer> {
}
