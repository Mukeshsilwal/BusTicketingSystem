package com.Transaction.transaction.service;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.payloads.BusInfoDto;

import java.time.LocalDateTime;
import java.util.List;

public interface BusInfoService {
    BusInfoDto createBusInfo(BusInfoDto busInfoDto);
    BusInfoDto updateBusInfo(BusInfoDto busInfoDto,int id,int routeId);
    void deleteBusInfo(Integer id);
    BusInfoDto createBusForRoute(BusInfoDto busInfoDto,int id);
    List<BusInfoDto> getAllBusInfo();
    List<BusInfoDto> getBusByDestination(LocalDateTime time, String source, String destination);
}
