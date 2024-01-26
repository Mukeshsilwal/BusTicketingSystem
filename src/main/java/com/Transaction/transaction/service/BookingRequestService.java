package com.Transaction.transaction.service;

import com.Transaction.transaction.model.ReservationResponse;
import com.Transaction.transaction.payloads.BookingRequestDto;

public interface BookingRequestService {
ReservationResponse rserveSeats(BookingRequestDto requestDto);
void cancelReservation(int bookingId);
public void associateSeatWithBooking(int seatId, int bookingRequestId);
}
