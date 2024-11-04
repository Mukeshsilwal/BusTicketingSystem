package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.Route12;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepo extends JpaRepository<Route12, Integer> {
    List<Route12> findByDestinationBusStop(Route12 route12);
}
