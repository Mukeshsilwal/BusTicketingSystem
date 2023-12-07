package com.Transaction.transaction.controller;

import com.Transaction.transaction.entity.BookingRequest;
import com.Transaction.transaction.model.ReservationResponse;
import com.Transaction.transaction.payloads.BookingRequestDto;
import com.Transaction.transaction.service.BookingRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookSeats")
public class BookingRequestController {
    private final BookingRequestService bookingRequestService;

    public BookingRequestController(BookingRequestService bookingRequestService) {
        this.bookingRequestService = bookingRequestService;
    }

    @PostMapping("/")
    public ResponseEntity<ReservationResponse> reserveSeats(@RequestBody BookingRequestDto requestDto){
        ReservationResponse reservationResponse=bookingRequestService.rserveSeats(requestDto);
        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);

    }
}
