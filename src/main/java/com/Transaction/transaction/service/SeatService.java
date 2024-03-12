package com.Transaction.transaction.service;


import com.Transaction.transaction.payloads.SeatDto;

import java.util.List;

public interface SeatService {
SeatDto updateSeat(SeatDto seatDto,int id);
void deleteSeat(int id);
SeatDto getSeatById(int id);
List<SeatDto> getAllSeat();
SeatDto createSeatForBus(SeatDto seatDto,int id);
List<SeatDto> findSeatRelatedToBus(String busName);


}
