package com.Transaction.transaction.unitTesting.unitTesting.uniiTesting;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.repository.BusInfoRepo;
import com.Transaction.transaction.repository.RouteRepo;
import com.Transaction.transaction.service.serviceImpl.BusInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BusInfoServiceTest {
    public BusInfoRepo busInfoRepo;
    public RouteRepo routeRepo;
    public ModelMapper modelMapper;
    public BusInfoServiceImpl busInfoService;

    @BeforeEach
    public void setUp() {
        busInfoRepo = mock(BusInfoRepo.class);
        routeRepo = mock(RouteRepo.class);
        modelMapper = new ModelMapper();
        busInfoService = new BusInfoServiceImpl(busInfoRepo, modelMapper, routeRepo);
    }

    @Test
    public void testForDeleteBusInfo() {
        int id = 1;
        BusInfo busInfo = new BusInfo();
        when(busInfoRepo.findById(1)).thenReturn(Optional.of(busInfo));
        busInfoService.deleteBusInfo(id);
        verify(busInfoRepo, times(1)).findById(id);
        verify(busInfoRepo, times(1)).delete(busInfo);
    }

    @Test
    public void updateBusInfoTest() {
        int id = 1;
        int id1 = 2;
        BusInfoDto busInfoDto = new BusInfoDto();
        BusInfo busInfo = new BusInfo();
        Route12 route12 = new Route12();
        when(busInfoRepo.findById(id)).thenReturn(Optional.of(busInfo));
        when(routeRepo.findById(id1)).thenReturn(Optional.of(route12));
        when(busInfoRepo.save(any(BusInfo.class))).thenReturn(busInfo);
        BusInfoDto busInfoDto1 = busInfoService.updateBusInfo(busInfoDto, id, id1);
        assertNotNull(busInfoDto1);
        verify(busInfoRepo, times(1)).findById(id);
        verify(routeRepo, times(1)).findById(id1);
        verify(busInfoRepo, times(1)).save(any(BusInfo.class));
    }

    @Test
    public void createBusInfoWithRouteTest() {
        int id1 = 2;
        BusInfoDto busInfoDto = new BusInfoDto();
        BusInfo busInfo = new BusInfo();
        Route12 route12 = new Route12();
        when(routeRepo.findById(id1)).thenReturn(Optional.of(route12));
        when(busInfoRepo.save(any(BusInfo.class))).thenReturn(busInfo);
        BusInfoDto busInfoDto1 = busInfoService.createBusForRoute(busInfoDto, id1);
        assertNotNull(busInfoDto1);
        verify(busInfoRepo, times(1)).save(any(BusInfo.class));
        verify(routeRepo, times(1)).findById(id1);

    }

    @Test
    public void getAllBusInfoTest() {
        List<BusInfo> busInfos = new ArrayList<>();
        when(busInfoRepo.findAll()).thenReturn(busInfos);
        List<BusInfoDto> busInfos1 = busInfoService.getAllBusInfo();
        assertNotNull(busInfos1);
        verify(busInfoRepo, times(1)).findAll();
    }
}
