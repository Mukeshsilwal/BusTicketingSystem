package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.BusStopDistanceDto;

import java.util.List;

public interface BusStopDistanceService {
    BusStopDistanceDto createBusStopDistanceWithBusStop(BusStopDistanceDto busStopDistance, int id, int id1);
    List<BusStopDistanceDto> getAllBusStopDistance();

}
