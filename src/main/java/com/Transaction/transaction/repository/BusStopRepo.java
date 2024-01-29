package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusStopRepo extends JpaRepository<BusStop,Integer>{

}
