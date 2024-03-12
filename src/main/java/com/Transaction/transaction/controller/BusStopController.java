package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.BusStopDto;
import com.Transaction.transaction.service.BusStopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/busStop")
@RequiredArgsConstructor
public class BusStopController {
    private final BusStopService busStopService;
    @GetMapping("/get")
    public ResponseEntity<List<BusStopDto>>  getAllBusStops(){
        List<BusStopDto> busStopDtos=this.busStopService.getAllBusStops();
        return new ResponseEntity<>(busStopDtos, HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<BusStopDto> getById(@PathVariable int id){
        BusStopDto busStopDto=this.busStopService.getBusStopById(id);
        return new ResponseEntity<>(busStopDto,HttpStatus.OK);
    }
}
