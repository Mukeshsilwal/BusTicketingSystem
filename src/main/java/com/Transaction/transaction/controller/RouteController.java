package com.Transaction.transaction.controller;



import com.Transaction.transaction.payloads.Route12Dto;
import com.Transaction.transaction.service.Route12Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {
    private final Route12Service route12Service;

    @GetMapping("/get/{id}")
    public ResponseEntity<Route12Dto> getRouteById(@PathVariable int id){
        Route12Dto route12Dto=this.route12Service.getRouteById(id);
        return new ResponseEntity<>(route12Dto,HttpStatus.OK);
    }
    @GetMapping("/get")
    public ResponseEntity<List<Route12Dto>> getAllRoute(){
        List<Route12Dto> route12Dtos=this.route12Service.getAllRoute();
        return new ResponseEntity<>(route12Dtos,HttpStatus.OK);
    }

}
