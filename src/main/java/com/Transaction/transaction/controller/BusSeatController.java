package com.Transaction.transaction.controller;

import com.Transaction.transaction.algorithm.SeatAllocationService;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.SeatsNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus1")
public class BusSeatController {
    private final SeatAllocationService seatAllocationService;

    @Autowired
    public BusSeatController(SeatAllocationService seatAllocationService) {
        this.seatAllocationService = seatAllocationService;
    }

    @GetMapping("/allocateSeat")
    public ResponseEntity<String> allocateSeat(@RequestParam String chosenZone) {
        if (seatAllocationService.hasAvailableSeats()) {
            Seat allocatedSeat = seatAllocationService.allocateSeat(chosenZone);
            if (allocatedSeat != null) {
                return ResponseEntity.ok("Seat allocated: " + allocatedSeat.getSeatNumber());
            } else {
                throw new SeatsNotAvailableException("No available seats in the chosen zone");
            }
        } else {
            throw new SeatsNotAvailableException("No available seats");
        }
    }
}

