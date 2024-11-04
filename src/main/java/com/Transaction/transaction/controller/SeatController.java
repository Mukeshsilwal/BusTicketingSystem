package com.Transaction.transaction.controller;

import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/get")
    public ResponseEntity<List<SeatDto>> getAllSeat() {
        List<SeatDto> seatDto = this.seatService.getAllSeat();
        return new ResponseEntity<>(seatDto, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SeatDto> getSeatById(@PathVariable Integer id) {
        SeatDto seatDto = this.seatService.getSeatById(id);
        return new ResponseEntity<>(seatDto, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<SeatDto>> getBusByBusName(@RequestParam String busName) {
        List<SeatDto> dtos = this.seatService.findSeatRelatedToBus(busName);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
