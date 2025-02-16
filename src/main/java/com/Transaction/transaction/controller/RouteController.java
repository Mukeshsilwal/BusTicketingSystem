package com.Transaction.transaction.controller;


import com.Transaction.transaction.payloads.RouteDto;
import com.Transaction.transaction.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService route12Service;

    @GetMapping("/get/{id}")
    public ResponseEntity<RouteDto> getRouteById(@PathVariable int id) {
        RouteDto routeDto = this.route12Service.getRouteById(id);
        return new ResponseEntity<>(routeDto, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<RouteDto>> getAllRoute() {
        List<RouteDto> routeDtos = this.route12Service.getAllRoute();
        return new ResponseEntity<>(routeDtos, HttpStatus.OK);
    }

}
