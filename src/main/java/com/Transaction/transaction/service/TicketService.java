package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.TicketDto;

import java.util.List;

public interface TicketService {

    void deleteTicket(int id);
    TicketDto updateTicket(TicketDto ticketDto,int tId);
    List<TicketDto> getAllTicket();
    TicketDto createTicketWithBooking(TicketDto ticketDto,int id);
    void deleteTicketWithBooking(int tId,int id,int bId);
    TicketDto updateTicketWithBooking(TicketDto ticketDto,int id);
    TicketDto createSeatWithTicket(TicketDto ticketDto,int id,int bId);
    void deleteSeatWithTicket(int tId,int id);
    TicketDto getTicketById(int tId);
    TicketDto createTicket(TicketDto ticketDto);
}
