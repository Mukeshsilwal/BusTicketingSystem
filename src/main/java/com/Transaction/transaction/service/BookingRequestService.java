package com.Transaction.transaction.service;

import com.Transaction.transaction.model.ReservationResponse;
import com.Transaction.transaction.payloads.BookingRequestDto;

import java.time.LocalDateTime;
import java.util.Date;

public interface BookingRequestService {
    ReservationResponse rserveSeat(BookingRequestDto requestDto, int seatID);

    void cancelReservation(String email, int ticketNo, int bookingId);

    void cancelNotification(String email);
}
