package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.TicketDto;

import java.util.List;

public interface TicketService {

    TicketDto updateTicket(TicketDto ticketDto,int tId);
    List<TicketDto> getAllTicket();
    TicketDto createTicketWithBooking(TicketDto ticketDto,int id);
    TicketDto updateTicketWithBooking(TicketDto ticketDto,int id);
    TicketDto createSeatWithTicket(TicketDto ticketDto,int id,int bId);
    void deleteSeatWithTicket(int tId);
    TicketDto getTicketById(int tId);
    TicketDto createTicket(TicketDto ticketDto);
    public void sendBookingConfirmationEmail(String userEmail,byte[] pdfContent);
}
