package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.BusDto;

import java.time.LocalDate;
import java.util.List;

public interface BusService {
    BusDto updateBusInfo(BusDto busDto, int id, int routeId);

    void deleteBusInfo(Integer id);

    BusDto createBusForRoute(BusDto busDto, int id);

    List<BusDto> getAllBusInfo();

    List<BusDto> getBusByRoute(String source, String destination, LocalDate time);
}
