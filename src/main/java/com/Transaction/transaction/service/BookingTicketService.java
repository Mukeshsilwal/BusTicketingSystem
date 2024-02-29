package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.BookingTicketDto;

import java.util.List;

public interface BookingTicketService {
    List<BookingTicketDto> getAllBookingWithUser();
    BookingTicketDto getBooking(int bokingId);
    BookingTicketDto createBookingWithUser(BookingTicketDto bookingTicketDto);
    BookingTicketDto getBookById(int id);

}
