package com.Transaction.transaction.service;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.payloads.Route12Dto;

import java.time.LocalDateTime;
import java.util.List;

public interface BusInfoService {
    BusInfoDto createBusInfo(BusInfoDto busInfoDto);
    BusInfoDto updateBusInfo(BusInfoDto busInfoDto,int id,int routeId);
    void deleteBusInfo(Integer id);
    BusInfoDto createBusForRoute(BusInfoDto busInfoDto,int id,int busId);
    List<BusInfoDto> getAllBusInfo();
    List<BusInfoDto> getBusByRoute(String source,String destination);
}
