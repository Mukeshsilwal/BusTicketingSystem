package com.Transaction.transaction.unitTesting.unitTesting.uniiTesting;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.repository.BusInfoRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.serviceImpl.SeatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SeatServiceTest {
    private SeatRepo seatRepo;
    private  BusInfoRepo busInfoRepo;
    private SeatServiceImpl seatService;

    @BeforeEach
    public void setUp(){
        seatRepo=mock(SeatRepo.class);
        busInfoRepo=mock(BusInfoRepo.class);
        ModelMapper modelMapper = new ModelMapper();
        seatService=new SeatServiceImpl(seatRepo, modelMapper,busInfoRepo);
    }

    @Test
    public void createRouteTest(){
        SeatDto seatDto=new SeatDto();
        Seat seat=new Seat();
        when(seatRepo.save(any(Seat.class))).thenReturn(seat);
        SeatDto seatDto1=seatService.createSeat(seatDto);
        assertNotNull(seatDto1);
        verify(seatRepo,times(1)).save(any(Seat.class));
    }
    @Test
    public void updateTest(){
        int id=1;
        SeatDto seatDto=new SeatDto();
        Seat seat=new Seat();
        when(seatRepo.findById(id)).thenReturn(Optional.of(seat));
        when(seatRepo.save(any(Seat.class))).thenReturn(seat);
        SeatDto seatDto1=seatService.updateSeat(seatDto,id);
        assertNotNull(seatDto1);
        verify(seatRepo,times(1)).findById(id);
        verify(seatRepo,times(1)).save(any(Seat.class));
    }
    @Test
    public void deleteTest(){
        int id=1;
        Seat seat=new Seat();
        when(seatRepo.findById(id)).thenReturn(Optional.of(seat));
        seatService.deleteSeat(id);
        verify(seatRepo,times(1)).findById(id);
    }
    @Test
    public void getSeatByIdTest(){
        int id=1;
        Seat seat=new Seat();
        when(seatRepo.findById(id)).thenReturn(Optional.of(seat));
        SeatDto seatDto1=seatService.getSeatById(id);
        assertNotNull(seatDto1);
        verify(seatRepo,times(1)).findById(id);
    }
    @Test
    public void createSeatForBus(){
        int id=1;
        Seat  seat=new Seat();
        SeatDto seatDto=new SeatDto();
        BusInfo busInfo=new BusInfo();
        when(busInfoRepo.findById(id)).thenReturn(Optional.of(busInfo));
        when(seatRepo.save(any(Seat.class))).thenReturn(seat);
        SeatDto seatDto1=seatService.createSeatForBus(seatDto,id);
        assertNotNull(seatDto1);
        verify(seatRepo,times(1)).save(any(Seat.class));
        verify(busInfoRepo,times(1)).findById(id);

    }
}
