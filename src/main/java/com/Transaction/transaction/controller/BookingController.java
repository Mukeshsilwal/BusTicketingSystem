package com.Transaction.transaction.controller;


import com.Transaction.transaction.payloads.BookingTicketDto;
import com.Transaction.transaction.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @GetMapping("/get")
    public ResponseEntity<List<BookingTicketDto>> getAllBookingTicket(){
        List<BookingTicketDto> bookingTicket=this.bookingService.getAllBookingWithUserAndTicket();
        return new ResponseEntity<>(bookingTicket, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingTicketDto> getBookingTicketById(@PathVariable Integer id){
        BookingTicketDto ticketDto=this.bookingService.getTicketByBookingTicketNo(id);
        return new ResponseEntity<>(ticketDto,HttpStatus.OK);
    }
    @PostMapping("/post")
    public ResponseEntity<BookingTicketDto> createBooking(@RequestBody BookingTicketDto bookingTicketDto){
        BookingTicketDto ticketDto=this.bookingService.createBooking(bookingTicketDto);
        return new ResponseEntity<>(ticketDto,HttpStatus.CREATED);
    }




    @GetMapping("/book/{id}")
    public ResponseEntity<BookingTicketDto> getSeat(@PathVariable Integer id){
       BookingTicketDto ticketDto=this.bookingService.getBookById(id);
        return new ResponseEntity<>(ticketDto,HttpStatus.OK);
    }

}
