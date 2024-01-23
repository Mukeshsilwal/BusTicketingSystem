package com.Transaction.transaction.service;


import com.Transaction.transaction.model.SeatType;
import com.Transaction.transaction.payloads.SeatDto;

import java.util.List;

public interface SeatService {
SeatDto createSeat(SeatDto seatDto);
SeatDto updateSeat(SeatDto seatDto,int id);
void deleteSeat(int id);
SeatDto getSeatById(int id);
List<SeatDto> getAllSeat();
SeatDto createSeatForBus(SeatDto seatDto,int id);
//void deleteBookingSeat(int sId,int id);
SeatType getSeatType(int id);
List<SeatDto> reserveSeat(int numSeat,SeatType preference);
SeatDto createReservedSeat(SeatDto seatDto,int id);

}
