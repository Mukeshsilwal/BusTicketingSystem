package com.Transaction.transaction.service;


import com.Transaction.transaction.model.CustomerPreferences;
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
List<SeatDto> findSeatRelatedToBus(String busName);
List<SeatDto> allocateSeatsWithPreferences(List<SeatDto> seats, int numberOfSeatsToAllocate, CustomerPreferences preferences);


}
