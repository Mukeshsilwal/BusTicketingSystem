package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.BusDto;
import com.Transaction.transaction.payloads.BusStopDto;
import com.Transaction.transaction.payloads.RouteDto;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.service.BusService;
import com.Transaction.transaction.service.BusStopService;
import com.Transaction.transaction.service.RouteService;
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
    private final RouteService service;
    private final BusService busService;
    private final SeatService seatService;

    @PutMapping("/updateBusStop/{id}")
    public ResponseEntity<BusStopDto> updateBusStop(@RequestBody BusStopDto busStopDto, @PathVariable int id) {
        BusStopDto busStopDto1 = this.stopService.updateBusStop(busStopDto, id);
        return new ResponseEntity<>(busStopDto1, HttpStatus.OK);
    }

    @PutMapping("/updateRoute/{id}")
    public ResponseEntity<RouteDto> updateRoute(@RequestBody RouteDto routeDto, @PathVariable int id) {
        RouteDto routeDto1 = this.service.updateRoute(routeDto, id);
        return new ResponseEntity<>(routeDto1, HttpStatus.OK);
    }

    @PutMapping("/bus/{id}/route/{routeId}")
    public ResponseEntity<BusDto> updateBusInfoWithRoute(@RequestBody BusDto busDto, @PathVariable Integer id, @PathVariable Integer routeId) {
        BusDto busDto1 = this.busService.updateBusInfo(busDto, id, routeId);
        return new ResponseEntity<>(busDto1, HttpStatus.OK);
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
    public ResponseEntity<RouteDto> createRouteWithBusStop(@RequestBody RouteDto routeDto, @PathVariable int
            id, @PathVariable int id1) {
        RouteDto routeDto1 = this.service.createRouteWithBusStop(routeDto, id, id1);
        return new ResponseEntity<>(routeDto1, HttpStatus.CREATED);
    }

    @PostMapping("/routeBus/{id}")
    public ResponseEntity<BusDto> createBusInRoute(@RequestBody BusDto busDto, @PathVariable int id) {
        BusDto busDto1 = this.busService.createBusForRoute(busDto, id);
        return new ResponseEntity<>(busDto1, HttpStatus.CREATED);
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
        busService.deleteBusInfo(id);
        return new ResponseEntity<>(new ApiResponse("Bus deleted", true, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSeat/{id}")
    public ResponseEntity<ApiResponse> deleteSeat(@PathVariable Integer id) {
        this.seatService.deleteSeat(id);
        return new ResponseEntity<>(new ApiResponse("Seat Has Been Deleted Successfully", true, HttpStatus.OK), HttpStatus.OK);
    }

}
