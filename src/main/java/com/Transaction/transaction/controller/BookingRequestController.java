package com.Transaction.transaction.controller;


import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.model.ReservationResponse;
import com.Transaction.transaction.payloads.BookingRequestDto;
import com.Transaction.transaction.service.BookingRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookSeats")
public class BookingRequestController {
    private final BookingRequestService bookingRequestService;

    @PostMapping("/{seatId}")
    public ResponseEntity<ReservationResponse> reserveSeats(@RequestBody BookingRequestDto requestDto, @PathVariable int seatId) {
        ReservationResponse reservationResponse = bookingRequestService.rserveSeat(requestDto, seatId);
        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> cancelSeat(@RequestParam String email, @RequestParam int ticketNo, @PathVariable int id) {
        bookingRequestService.cancelReservation(email, ticketNo, id);
        bookingRequestService.cancelNotification(email);
        return new ResponseEntity<>(new ApiResponse("Seat has been canceled", true, HttpStatus.OK), HttpStatus.OK);
    }
}
