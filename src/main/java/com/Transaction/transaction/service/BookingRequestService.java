package com.Transaction.transaction.service;

import com.Transaction.transaction.model.ReservationResponse;
import com.Transaction.transaction.payloads.BookingRequestDto;

import java.time.LocalDate;
import java.util.Date;

public interface BookingRequestService {
ReservationResponse rserveSeat(BookingRequestDto requestDto,int seatID);
void cancelReservation(String email, int ticketNo, Date date, int bookingId);
public void associateSeatWithBooking(int seatId, int bookingRequestId);
}
