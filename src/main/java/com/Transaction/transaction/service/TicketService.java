package com.Transaction.transaction.service;

import com.Transaction.transaction.entity.Ticket;
import com.Transaction.transaction.payloads.TicketDto;

import java.util.List;

public interface TicketService {

    TicketDto updateTicket(TicketDto ticketDto, int tId);

    TicketDto createSeatWithTicket(TicketDto ticketDto, int id, int bookId);

    void deleteSeatWithTicket(int tId);

    Ticket getTicketById(int tId);

    void sendBookingConfirmationEmail(String userEmail, byte[] pdfContent);
}
