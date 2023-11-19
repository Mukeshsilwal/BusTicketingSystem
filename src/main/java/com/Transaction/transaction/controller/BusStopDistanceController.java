package com.Transaction.transaction.controller;

import com.Transaction.transaction.payloads.BusStopDistanceDto;
import com.Transaction.transaction.service.BusStopDistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/distance")
@RequiredArgsConstructor
public class BusStopDistanceController {
    private final BusStopDistanceService stopDistanceService;
    @PostMapping("/source/{id}/destination/{id1}")
    public ResponseEntity<BusStopDistanceDto> createDistance(@RequestBody BusStopDistanceDto busStopDistanceDto,
                                                             @PathVariable int id,@PathVariable int id1){
        BusStopDistanceDto busStopDistanceDto1=this.stopDistanceService.createBusStopDistanceWithBusStop(busStopDistanceDto,id,id1);
        return new ResponseEntity<>(busStopDistanceDto1, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<BusStopDistanceDto>> getAllBusStopDistance(){
        List<BusStopDistanceDto> busStopDistanceDtos=this.stopDistanceService.getAllBusStopDistance();
        return new ResponseEntity<>(busStopDistanceDtos,HttpStatus.OK);
    }

}
