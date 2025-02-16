package com.Transaction.transaction.controller;

import com.Transaction.transaction.payloads.BusDto;
import com.Transaction.transaction.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusInfoController {
    private final BusService busService;

    @GetMapping("/route")
    public ResponseEntity<List<BusDto>> getAllRoute() {
        List<BusDto> busInfo = this.busService.getAllBusInfo();
        return new ResponseEntity<>(busInfo, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BusDto>> searchBuses(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<BusDto> busDtos = busService.getBusByRoute(source, destination, date);

        return new ResponseEntity<>(busDtos, HttpStatus.OK);
    }


}
