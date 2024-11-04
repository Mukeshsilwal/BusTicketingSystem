package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.payloads.BusStopDto;
import com.Transaction.transaction.payloads.Route12Dto;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.service.BusInfoService;
import com.Transaction.transaction.service.BusStopService;
import com.Transaction.transaction.service.Route12Service;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class SingleAdminController {
    private final BusStopService stopService;
    private final Route12Service service;
    private final BusInfoService busInfoService;
    private final SeatService seatService;

    @PutMapping("/updateBusStop/{id}")
    public ResponseEntity<BusStopDto> updateBusStop(@RequestBody BusStopDto busStopDto, @PathVariable int id) {
        BusStopDto busStopDto1 = this.stopService.updateBusStop(busStopDto, id);
        return new ResponseEntity<>(busStopDto1, HttpStatus.OK);
    }

    @PutMapping("/updateRoute/{id}")
    public ResponseEntity<Route12Dto> updateRoute(@RequestBody Route12Dto route12Dto, @PathVariable int id) {
        Route12Dto route12Dto1 = this.service.updateRoute(route12Dto, id);
        return new ResponseEntity<>(route12Dto1, HttpStatus.OK);
    }

    @PutMapping("/bus/{id}/route/{routeId}")
    public ResponseEntity<BusInfoDto> updateBusInfoWithRoute(@RequestBody BusInfoDto busInfoDto, @PathVariable Integer id, @PathVariable Integer routeId) {
        BusInfoDto busInfoDto1 = this.busInfoService.updateBusInfo(busInfoDto, id, routeId);
        return new ResponseEntity<>(busInfoDto1, HttpStatus.OK);
    }

    @PutMapping("/updateSeat/{id}")
    public ResponseEntity<SeatDto> updateSeat(@RequestBody SeatDto seatDto, @PathVariable Integer id) {
        SeatDto seatDto1 = this.seatService.updateSeat(seatDto, id);
        return new ResponseEntity<>(seatDto1, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<BusStopDto> createBusStop(@RequestBody BusStopDto busStopDto) {
        BusStopDto busStopDto1 = this.stopService.createBusStop(busStopDto);
        return new ResponseEntity<>(busStopDto1, HttpStatus.CREATED);
    }

    @PostMapping("/busStopRoute/{id}/{id1}")
    public ResponseEntity<Route12Dto> createRouteWithBusStop(@RequestBody Route12Dto route12Dto, @PathVariable int
            id, @PathVariable int id1) {
        Route12Dto route12Dto1 = this.service.createRouteWithBusStop(route12Dto, id, id1);
        return new ResponseEntity<>(route12Dto1, HttpStatus.CREATED);
    }

    @PostMapping("/routeBus/{id}")
    public ResponseEntity<BusInfoDto> createBusInRoute(@RequestBody BusInfoDto busInfoDto, @PathVariable int id) {
        BusInfoDto busInfoDto1 = this.busInfoService.createBusForRoute(busInfoDto, id);
        return new ResponseEntity<>(busInfoDto1, HttpStatus.CREATED);
    }

    @PostMapping("/postSeat/{id}")
    public ResponseEntity<SeatDto> createSeatForBus(@RequestBody SeatDto seatDto, @PathVariable Integer id) {
        SeatDto seatDto1 = this.seatService.createSeatForBus(seatDto, id);
        return new ResponseEntity<>(seatDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteBusStop/{id}")
    public ResponseEntity<ApiResponse> deleteBusStop(@PathVariable int id) {
        this.stopService.deleteBusStop(id);
        return new ResponseEntity<>(new ApiResponse("BusStop has been deleted", true, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoute/{id}")
    public ResponseEntity<ApiResponse> deleteRoute(@PathVariable int id) {
        this.service.deleteRoute(id);
        return new ResponseEntity<>(new ApiResponse("Route is deleted by ADMIN", true, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBus/{id}")
    public ResponseEntity<ApiResponse> deleteBus(@PathVariable int id) {
        busInfoService.deleteBusInfo(id);
        return new ResponseEntity<>(new ApiResponse("Bus deleted", true, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSeat/{id}")
    public ResponseEntity<ApiResponse> deleteSeat(@PathVariable Integer id) {
        this.seatService.deleteSeat(id);
        return new ResponseEntity<>(new ApiResponse("Seat Has Been Deleted Successfully", true, HttpStatus.OK), HttpStatus.OK);
    }

}
