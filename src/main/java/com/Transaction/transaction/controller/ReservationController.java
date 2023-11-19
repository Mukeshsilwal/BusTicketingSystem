package com.Transaction.transaction.controller;

import com.Transaction.transaction.entity.Reservation;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReservationController {
    private final SeatService seatService;

    @PostMapping("/res")
    public ResponseEntity<List<SeatDto>> reserve(@RequestBody Reservation reservation){
        List<SeatDto> seatDtos=this.seatService.reserveSeat(reservation.getNumSeat(), reservation.getPreference());
        return new ResponseEntity<>(seatDtos, HttpStatus.CREATED);

    }
}
