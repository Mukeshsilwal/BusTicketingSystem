package com.Transaction.transaction.unitTesting.unitTesting.uniiTesting;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.payloads.BusDto;
import com.Transaction.transaction.repository.BusRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.serviceImpl.BusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BusServiceTest {
    public BusRepo busRepo;
    public RouteRepo routeRepo;
    public ModelMapper modelMapper;
    public BusServiceImpl busInfoService;

    @BeforeEach
    public void setUp() {
        busRepo = mock(BusRepo.class);
        routeRepo = mock(RouteRepo.class);
        modelMapper = new ModelMapper();
        busInfoService = new BusServiceImpl(busRepo, modelMapper, routeRepo);
    }

    @Test
    public void testForDeleteBusInfo() {
        int id = 1;
        BusInfo busInfo = new BusInfo();
        when(busRepo.findById(1)).thenReturn(Optional.of(busInfo));
        busInfoService.deleteBusInfo(id);
        verify(busRepo, times(1)).findById(id);
        verify(busRepo, times(1)).delete(busInfo);
    }

    @Test
    public void updateBusInfoTest() {
        int id = 1;
        int id1 = 2;
        BusDto busDto = new BusDto();
        BusInfo busInfo = new BusInfo();
        Route12 route12 = new Route12();
        when(busRepo.findById(id)).thenReturn(Optional.of(busInfo));
        when(routeRepo.findById(id1)).thenReturn(Optional.of(route12));
        when(busRepo.save(any(BusInfo.class))).thenReturn(busInfo);
        BusDto busDto1 = busInfoService.updateBusInfo(busDto, id, id1);
        assertNotNull(busDto1);
        verify(busRepo, times(1)).findById(id);
        verify(routeRepo, times(1)).findById(id1);
        verify(busRepo, times(1)).save(any(BusInfo.class));
    }

    @Test
    public void createBusInfoWithRouteTest() {
        int id1 = 2;
        BusDto busDto = new BusDto();
        BusInfo busInfo = new BusInfo();
        Route12 route12 = new Route12();
        when(routeRepo.findById(id1)).thenReturn(Optional.of(route12));
        when(busRepo.save(any(BusInfo.class))).thenReturn(busInfo);
        BusDto busDto1 = busInfoService.createBusForRoute(busDto, id1);
        assertNotNull(busDto1);
        verify(busRepo, times(1)).save(any(BusInfo.class));
        verify(routeRepo, times(1)).findById(id1);

    }

    @Test
    public void getAllBusInfoTest() {
        List<BusInfo> busInfos = new ArrayList<>();
        when(busRepo.findAll()).thenReturn(busInfos);
        List<BusDto> busInfos1 = busInfoService.getAllBusInfo();
        assertNotNull(busInfos1);
        verify(busRepo, times(1)).findAll();
    }
}
