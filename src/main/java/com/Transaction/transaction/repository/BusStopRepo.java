package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusStopRepo extends JpaRepository<BusStop,Integer>{
}
