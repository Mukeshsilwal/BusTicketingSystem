package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.BookingTicket;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.entity.Ticket;
import com.Transaction.transaction.exception.DuplicateEntryException;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.TicketDto;
import com.Transaction.transaction.repository.BookingRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.repository.TicketRepo;
import com.Transaction.transaction.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepo ticketRepo;
    private final ModelMapper modelMapper;
    private final BookingRepo bookingRepo;
    private final SeatRepo seatRepo;
    private final EmailService emailService;

    @Override
    public void sendBookingConfirmationEmail(String userEmail, byte[] pdfContent) {
        String subject = "Booking Confirmation";
        String body = "Thank you for booking! Your booking details: ";
        String attachmentName = "ticket.pdf";

//        emailService.sendEmail(userEmail, subject, body, pdfContent, attachmentName);
    }

    @Override
    public TicketDto updateTicket(TicketDto ticketDto, int tId) {
        Ticket ticket = this.ticketRepo.findById(tId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "tId", tId));
        ticket.setTicketNo(ticketDto.getTicketNo());
        return ticketToDto(ticket);
    }

    @Override
    public TicketDto createSeatWithTicket(TicketDto ticketDto, int id, int bookId) {
        try {
            Ticket ticket = this.dtoToTicket(ticketDto);
            Seat seat = this.seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
            BookingTicket bookingTicket = this.bookingRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("BookingTicket", "bId", bookId));
            ticket.setSeat(seat);
            ticket.setSeatNumber(seat.getSeatNumber());
            ticket.setBookingTicket(bookingTicket);
            Ticket ticket1 = this.ticketRepo.save(ticket);
            return ticketToDto(ticket1);
        } catch (DataIntegrityViolationException e) {
            // Catch the specific exception for duplicate entry
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new DuplicateEntryException("Ticket with the same entry already exists.");
            }
            // Rethrow the exception if it's not related to duplicate entry
            throw e;
        }
    }

    @Transactional
    @Override
    public void deleteSeatWithTicket(int tId) {
        Ticket ticket = this.ticketRepo.findById(tId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "tId", tId));
        Seat seat = ticket.getSeat();
        if (seat != null) {
            seat.setTicket(null);
            ticket.setSeat(null);
            seatRepo.save(seat);
        }
        BookingTicket ticket1 = ticket.getBookingTicket();
        if (ticket1 != null) {
            ticket1.getTicket().remove(ticket);
            bookingRepo.save(ticket1);
        }
        this.ticketRepo.delete(ticket);
    }

    @Override
    public Ticket getTicketById(int tId) {
        Ticket ticket = this.ticketRepo.findById(tId).orElseThrow(() -> new ResourceNotFoundException("Ticket", "tId", tId));
        return ticket;
    }

    public Ticket dtoToTicket(TicketDto ticketDto) {
        return this.modelMapper.map(ticketDto, Ticket.class);
    }

    public TicketDto ticketToDto(Ticket ticket) {
        return this.modelMapper.map(ticket, TicketDto.class);
    }
}
