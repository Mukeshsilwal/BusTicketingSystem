package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BusRepo extends JpaRepository<Bus, Integer> {
    List<Bus> findByRouteSourceBusStopNameAndRouteDestinationBusStopNameAndDate(
            String source, String destination, LocalDate time);

}
