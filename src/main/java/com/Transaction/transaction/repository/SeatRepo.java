package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BookingTicket;
import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface SeatRepo extends JpaRepository<Seat, Integer> {

    @Query("SELECT s FROM Seat s WHERE s.reserved = false ORDER BY s.id ASC")
    List<Seat> findFirstNByReservedFalse();

    List<Seat> findByBusInfoBusName(String busName);

    List<Seat> findByBusInfoAndReserved(BusInfo busInfo, boolean reserved);

    int countByBusInfo(BusInfo busInfo);

}
