package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.payloads.Route12Dto;
import com.Transaction.transaction.service.BusInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @GetMapping("/search")
    public ResponseEntity<List<BusInfoDto>> search(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,@RequestParam String source, @RequestParam String destination){
        List<BusInfoDto> busInfoDtos=this.busInfoService.getBusByDestination(departureDateTime,source,destination);
        return new ResponseEntity<>(busInfoDtos,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBus(@PathVariable int id){
        busInfoService.deleteBusInfo(id);
        return new ResponseEntity<>(new ApiResponse("Bus deleted",true,HttpStatus.OK),HttpStatus.OK);
    }
}
