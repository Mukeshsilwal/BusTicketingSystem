package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Route12;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BusInfoRepo extends JpaRepository<BusInfo,Integer> {
    List<BusInfo> findByRoute12SourceBusStopNameAndRoute12DestinationBusStopNameAndDepartureDateTime(
            String sourceBusStopName, String destinationBusStopName, Date date);


}
