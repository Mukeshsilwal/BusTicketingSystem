package com.Transaction.transaction.service.serviceImpl;


import com.Transaction.transaction.entity.BookingTicket;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.payloads.BookingTicketDto;
import com.Transaction.transaction.repository.BookingRepo;
import com.Transaction.transaction.service.BookingTicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingTicketServiceImpl implements BookingTicketService {
    private final BookingRepo bookingRepo;
    private final ModelMapper modelMapper;
    @Override
    public List<BookingTicketDto> getAllBooking() {
        List<BookingTicket> requests=this.bookingRepo.findAll();
        return requests.stream().map(this::bookingToDto).collect(Collectors.toList());
    }
    @Override
    public BookingTicketDto getBooking(int bookingId) {
        BookingTicket bookingTicket=this.bookingRepo.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("BookingTicket","bookingId",bookingId));
        return bookingToDto(bookingTicket);
    }
    @Override
    public BookingTicketDto createBooking(BookingTicketDto bookingTicketDto) {
        BookingTicket bookingTicket=this.dtoToBooking(bookingTicketDto);
        BookingTicket bookingTicket1=this.bookingRepo.save(bookingTicket);
        return bookingToDto(bookingTicket1);
    }
    @Override
    public BookingTicketDto getBookById(int id) {
        BookingTicket bookingTicket=this.bookingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat","id",id));
        return bookingToDto(bookingTicket);
    }
    public BookingTicket dtoToBooking(BookingTicketDto bookingTicketDto){
        return this.modelMapper.map(bookingTicketDto,BookingTicket.class);
    }
    public BookingTicketDto bookingToDto(BookingTicket bookingTicket){
        return this.modelMapper.map(bookingTicket, BookingTicketDto.class);
    }
}
