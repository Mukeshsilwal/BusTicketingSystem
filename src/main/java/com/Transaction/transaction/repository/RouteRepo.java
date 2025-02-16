package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepo extends JpaRepository<Route, Integer> {
    List<Route> findByDestinationBusStop(Route route12);
}
