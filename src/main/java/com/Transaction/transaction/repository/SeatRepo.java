package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.Bus;
import com.Transaction.transaction.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat, Integer> {

    @Query("SELECT s FROM Seat s WHERE s.reserved = false ORDER BY s.id ASC")
    List<Seat> findFirstByReservedFalse();

    List<Seat> findByBusBusName(String busName);

    List<Seat> findByBusAndReserved(Bus busInfo, boolean reserved);

    int countByBus(Bus bus);

}
