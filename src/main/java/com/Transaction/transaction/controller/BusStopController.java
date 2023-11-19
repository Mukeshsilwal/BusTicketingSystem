package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.BusInfoDto;
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
    @GetMapping("/")
    public ResponseEntity<List<BusStopDto>>  getAllBusStops(){
        List<BusStopDto> busStopDtos=this.busStopService.getAllBusStops();
        return new ResponseEntity<>(busStopDtos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BusStopDto> getById(@PathVariable int id){
        BusStopDto busStopDto=this.busStopService.getBusStopById(id);
        return new ResponseEntity<>(busStopDto,HttpStatus.OK);
    }
    @PostMapping("/post")
    public ResponseEntity<BusStopDto> createBusStop(@RequestBody BusStopDto busStopDto) {
        BusStopDto busStopDto1 = this.busStopService.createBusStop(busStopDto);
        return new ResponseEntity<>(busStopDto1, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<BusStopDto> updateBusStop(@RequestBody BusStopDto busStopDto,@PathVariable int id){
        BusStopDto busStopDto1=this.busStopService.updateBusStop(busStopDto,id);
        return new ResponseEntity<>(busStopDto1,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBusStop(@PathVariable int id){
        this.busStopService.deleteBusStop(id);
        return new ResponseEntity<>(new ApiResponse("BusStop has been deleted",true,HttpStatus.OK),HttpStatus.OK);
    }


}
