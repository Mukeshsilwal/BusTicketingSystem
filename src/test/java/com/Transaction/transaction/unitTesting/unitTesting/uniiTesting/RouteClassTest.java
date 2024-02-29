package com.Transaction.transaction.unitTesting.unitTesting.uniiTesting;


import com.Transaction.transaction.algorithm.AlgorithmShortestPath;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.payloads.Route12Dto;
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
    private AlgorithmShortestPath algorithmShortestPath;
    @BeforeEach
    public  void setUp(){
        routeRepo=mock(RouteRepo.class);
        ModelMapper modelMapper = new ModelMapper();
        routeService=new RouteServiceImpl(routeRepo, modelMapper, algorithmShortestPath, busStopRepo);
    }
    @Test
    public void testForDeleteMethod(){
        int id=1;
        Route12 route12=new Route12();
        when(routeRepo.findById(id)).thenReturn(Optional.of(route12));
        routeService.deleteRoute(id);
        verify(routeRepo,times(1)).findById(id);
        verify(routeRepo,times(1)).delete(route12);
    }
    @Test
    public void updateRoute(){
        int id=1;
        Route12Dto route12Dto=new Route12Dto();
        Route12 route12=new Route12();
        when(routeRepo.findById(id)).thenReturn(Optional.of(route12));
        when(routeRepo.save(any(Route12.class))).thenReturn(route12);
        Route12Dto result=routeService.updateRoute(route12Dto,id);
        assertNotNull(result);
        verify(routeRepo,times(1)).findById(id);
        verify(routeRepo,times(1)).save(any(Route12.class));

    }
    @Test
    public void createRoute(){
        Route12Dto route12Dto=new Route12Dto();
        Route12 route12=new Route12();
        when(routeRepo.save(any(Route12.class))).thenReturn(route12);
        Route12Dto route12Dto1=routeService.createRoute(route12Dto);
        assertNotNull(route12Dto1);
        verify(routeRepo,times(1)).save(any(Route12.class));
    }
    @Test
    public void getAllRoute() {
        List<Route12> route12s = new ArrayList<>();
        when(routeRepo.findAll()).thenReturn(route12s);
        List<Route12Dto> route12Dtos1 = routeService.getAllRoute();
        assertNotNull(route12Dtos1);
        verify(routeRepo, times(1)).findAll();
    }
    @Test
    public void getRouteById(){
        int id=1;
        Route12 route12=new Route12();
        when(routeRepo.findById(id)).thenReturn(Optional.of(route12));
        Route12Dto route12Dto=routeService.getRouteById(id);
        assertNotNull(route12Dto);
        verify(routeRepo,times(1)).findById(id);
    }
}
