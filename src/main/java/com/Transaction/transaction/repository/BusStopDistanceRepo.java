package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BusStopDistance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusStopDistanceRepo extends JpaRepository<BusStopDistance,Long> {
}
