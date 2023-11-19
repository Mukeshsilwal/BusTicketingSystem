package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.payloads.Route12Dto;
import com.Transaction.transaction.service.BusInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusInfoController {
    private final BusInfoService busInfoService;
    @PostMapping("/post")
    public ResponseEntity<BusInfoDto> createBusInfo(@RequestBody BusInfoDto busInfoDto){
        BusInfoDto busInfoDto1=this.busInfoService.createBusInfo(busInfoDto);
        return new ResponseEntity<>(busInfoDto1, HttpStatus.OK);
    }
    @PostMapping("/route/{id}")
    public ResponseEntity<BusInfoDto> createBusInRoute(@RequestBody BusInfoDto busInfoDto,@PathVariable Integer id){
        BusInfoDto busInfoDto1=this.busInfoService.createBusForRoute(busInfoDto,id);
        return new ResponseEntity<>(busInfoDto1,HttpStatus.CREATED);
    }
    @GetMapping("/route")
    public ResponseEntity<List<BusInfoDto>> getAllRoute(){
        List<BusInfoDto>  busInfo=this.busInfoService.getAllBusInfo();
        return new ResponseEntity<>(busInfo,HttpStatus.OK);
    }
    @PutMapping("/bus/{id}/route/{routeId}")
    public ResponseEntity<BusInfoDto> updateBusInfoWithRoute(@RequestBody BusInfoDto busInfoDto,@PathVariable Integer id,@PathVariable Integer routeId){
        BusInfoDto busInfoDto1=this.busInfoService.updateBusInfo(busInfoDto,id,routeId);
        return new ResponseEntity<>(busInfoDto1,HttpStatus.OK);
    }
    @DeleteMapping("/bus/{id}/route/{routeId}")
    public ResponseEntity<ApiResponse> deleteBusWithRoute(@PathVariable Integer id,@PathVariable Integer routeId){
        this.busInfoService.deleteBusInfoWithRoute(id,routeId);
        return new ResponseEntity<>(new ApiResponse("Bus With Route Hass Been Deleted",true,HttpStatus.OK),HttpStatus.OK);
    }
}
