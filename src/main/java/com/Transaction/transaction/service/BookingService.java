package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.BookingTicketDto;

import java.util.List;

public interface BookingService {
    List<BookingTicketDto> getAllBookingWithUserAndTicket();
    BookingTicketDto getTicketByBookingTicketNo(int bokingId);
    BookingTicketDto createBooking(BookingTicketDto bookingTicketDto);
    BookingTicketDto getBookById(int id);

}
