package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.BusInfoDto;
import java.util.List;

public interface BusInfoService {
    BusInfoDto updateBusInfo(BusInfoDto busInfoDto,int id,int routeId);
    void deleteBusInfo(Integer id);
    BusInfoDto createBusForRoute(BusInfoDto busInfoDto,int id);
    List<BusInfoDto> getAllBusInfo();
    List<BusInfoDto> getBusByRoute(String source, String destination);
}
