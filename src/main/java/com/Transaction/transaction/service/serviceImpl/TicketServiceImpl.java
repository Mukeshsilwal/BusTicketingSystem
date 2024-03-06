package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.BookingTicket;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.entity.Ticket;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.TicketDto;
import com.Transaction.transaction.repository.BookingRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.repository.TicketRepo;
import com.Transaction.transaction.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepo ticketRepo;
    private final ModelMapper modelMapper;
    private final BookingRepo bookingRepo;
    private final SeatRepo seatRepo;
    private final EmailService emailService;
    @Override
    public void deleteTicket(int id) {
        Ticket ticket=this.ticketRepo.findById(id).orElseThrow();
        this.ticketRepo.delete(ticket);
    }
    @Override
    public void sendBookingConfirmationEmail(String userEmail,byte[] pdfContent) {
        String subject = "Booking Confirmation";
        String body = "Thank you for booking! Your booking details: " ;
        String attachmentName = "ticket.pdf";

        emailService.sendEmail(userEmail, subject,body,pdfContent, attachmentName);
    }

    @Override
    public TicketDto updateTicket(TicketDto ticketDto, int tId) {
        Ticket ticket=this.ticketRepo.findById(tId).orElseThrow(()->new ResourceNotFoundException("Ticket","tId",tId));
        ticket.setTicketNo(ticketDto.getTicketNo());
        ticket.setPassengerName(ticketDto.getPassengerName());
        ticket.setSeatNo(ticketDto.getSeatNo());
        return ticketToDto(ticket);
    }

    @Override
    public List<TicketDto> getAllTicket() {
        List<Ticket> tickets=this.ticketRepo.findAll();
        return tickets.stream().map(this::ticketToDto).collect(Collectors.toList());
    }

    @Override
    public TicketDto createTicketWithBooking(TicketDto ticketDto, int id) {
        Ticket ticket=this.dtoToTicket(ticketDto);
        BookingTicket bookingTicket=this.bookingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookingTicket","id",id));
        ticket.setBookingTicket(bookingTicket);
        Ticket ticket1=this.ticketRepo.save(ticket);
        return ticketToDto(ticket1);
    }

    @Override
    public void deleteTicketWithBooking(int tId, int id,int bId) {
        BookingTicket bookingTicket=this.bookingRepo.findById(bId).orElseThrow(() -> new ResourceNotFoundException("BookingTicket","bId",bId));
        Ticket ticket=this.ticketRepo.findById(tId).orElseThrow(()->new ResourceNotFoundException("Ticket","tId",tId));
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
        this.ticketRepo.delete(ticket);
        this.bookingRepo.delete(bookingTicket);
        this.seatRepo.delete(seat);

    }

    @Override
    public TicketDto updateTicketWithBooking(TicketDto ticketDto, int id) {
        Ticket ticket=this.dtoToTicket(ticketDto);
        BookingTicket bookingTicket=this.bookingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookingTicket","id",id));
        ticket.setTicketNo(ticketDto.getTicketNo());
        ticket.setPassengerName(ticketDto.getPassengerName());
        ticket.setBookingTicket(bookingTicket);
        ticket.setDepartureDate(ticketDto.getDepartureDate());
        ticket.setSeatNo(ticketDto.getSeatNo());
        Ticket ticket1=this.ticketRepo.save(ticket);
        return ticketToDto(ticket1);
    }

    @Override
    public TicketDto createSeatWithTicket(TicketDto ticketDto, int id,int bId) {
        Ticket ticket=this.dtoToTicket(ticketDto);
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
        BookingTicket bookingTicket=this.bookingRepo.findById(bId).orElseThrow(() -> new ResourceNotFoundException("BookingTicket","bId",bId));
        ticket.setSeat(seat);
        ticket.setBookingTicket(bookingTicket);
        Ticket ticket1=this.ticketRepo.save(ticket);
        return ticketToDto(ticket1);
    }

    @Override
    public void deleteSeatWithTicket(int tId, int id) {
        Ticket ticket=this.ticketRepo.findById(tId).orElseThrow(()->new ResourceNotFoundException("Ticket","tId",tId));
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
        this.ticketRepo.delete(ticket);
        this.seatRepo.delete(seat);
    }

    @Override
    public TicketDto getTicketById(int tId) {
        Ticket ticket=this.ticketRepo.findById(tId).orElseThrow(()->new ResourceNotFoundException("Ticket","tId",tId));
        return ticketToDto(ticket);
    }

    @Override
    public TicketDto createTicket(TicketDto ticketDto) {
        Ticket ticket=this.dtoToTicket(ticketDto);
        Ticket ticket1=this.ticketRepo.save(ticket);
        return ticketToDto(ticket1);
    }

    public Ticket dtoToTicket(TicketDto ticketDto){
        return this.modelMapper.map(ticketDto,Ticket.class);
    }
    public TicketDto ticketToDto(Ticket ticket){
        return this.modelMapper.map(ticket, TicketDto.class);
    }
}
