package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.RouteDto;

import java.util.List;

public interface RouteService {
    RouteDto updateRoute(RouteDto routeDto, int id);

    void deleteRoute(int id);

    RouteDto getRouteById(int id);

    List<RouteDto> getAllRoute();

    RouteDto createRouteWithBusStop(RouteDto routeDto, int id, int id1);

}
