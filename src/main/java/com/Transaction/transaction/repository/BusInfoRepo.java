package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Route12;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BusInfoRepo extends JpaRepository<BusInfo, Integer> {
    List<BusInfo> findByRoute12SourceBusStopNameAndRoute12DestinationBusStopNameAndDate(
            String source, String destination, LocalDate time);

}
