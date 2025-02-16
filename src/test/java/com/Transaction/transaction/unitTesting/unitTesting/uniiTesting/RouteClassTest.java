package com.Transaction.transaction.unitTesting.unitTesting.uniiTesting;


import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.payloads.RouteDto;
import com.Transaction.transaction.repository.BusStopRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.serviceImpl.RouteServiceImpl;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RouteClassTest {

    private RouteRepo routeRepo;

    private RouteServiceImpl routeService;
    private BusStopRepo busStopRepo;

    @BeforeEach
    public void setUp() {
        routeRepo = mock(RouteRepo.class);
        ModelMapper modelMapper = new ModelMapper();
        routeService = new RouteServiceImpl(routeRepo, modelMapper, busStopRepo);
    }

    @Test
    public void testForDeleteMethod() {
        int id = 1;
        Route12 route12 = new Route12();
        when(routeRepo.findById(id)).thenReturn(Optional.of(route12));
        routeService.deleteRoute(id);
        verify(routeRepo, times(1)).findById(id);
        verify(routeRepo, times(1)).delete(route12);
    }

    @Test
    public void updateRoute() {
        int id = 1;
        RouteDto routeDto = new RouteDto();
        Route12 route12 = new Route12();
        when(routeRepo.findById(id)).thenReturn(Optional.of(route12));
        when(routeRepo.save(any(Route12.class))).thenReturn(route12);
        RouteDto result = routeService.updateRoute(routeDto, id);
        assertNotNull(result);
        verify(routeRepo, times(1)).findById(id);
        verify(routeRepo, times(1)).save(any(Route12.class));

    }

    @Test
    public void getAllRoute() {
        List<Route12> route12s = new ArrayList<>();
        when(routeRepo.findAll()).thenReturn(route12s);
        List<RouteDto> routeDtos1 = routeService.getAllRoute();
        assertNotNull(routeDtos1);
        verify(routeRepo, times(1)).findAll();
    }

    @Test
    public void getRouteById() {
        int id = 1;
        Route12 route12 = new Route12();
        when(routeRepo.findById(id)).thenReturn(Optional.of(route12));
        RouteDto routeDto = routeService.getRouteById(id);
        assertNotNull(routeDto);
        verify(routeRepo, times(1)).findById(id);
    }
}
