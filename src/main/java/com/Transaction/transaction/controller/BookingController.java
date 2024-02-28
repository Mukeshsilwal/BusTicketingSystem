package com.Transaction.transaction.controller;


import com.Transaction.transaction.payloads.BookingTicketDto;
import com.Transaction.transaction.service.BookingTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingTicketService bookingTicketService;
    @GetMapping("/get")
    public ResponseEntity<List<BookingTicketDto>> getAllBookingTicket(){
        List<BookingTicketDto> bookingTicket=this.bookingTicketService.getAllBookingWithUser();
        return new ResponseEntity<>(bookingTicket, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingTicketDto> getBookingTicketById(@PathVariable Integer id){
        BookingTicketDto ticketDto=this.bookingTicketService.getBooking(id);
        return new ResponseEntity<>(ticketDto,HttpStatus.OK);
    }
    @PostMapping("/post/{id}")
    public ResponseEntity<BookingTicketDto> createBooking(@RequestBody BookingTicketDto bookingTicketDto,@PathVariable int id){
        BookingTicketDto ticketDto=this.bookingTicketService.createBookingWithUser(bookingTicketDto,id);

        return new ResponseEntity<>(ticketDto,HttpStatus.CREATED);
    }
    @GetMapping("/book/{id}")
    public ResponseEntity<BookingTicketDto> getSeat(@PathVariable Integer id){
       BookingTicketDto ticketDto=this.bookingTicketService.getBookById(id);
        return new ResponseEntity<>(ticketDto,HttpStatus.OK);
    }

}
