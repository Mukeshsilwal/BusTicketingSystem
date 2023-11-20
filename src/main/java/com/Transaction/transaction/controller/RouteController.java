package com.Transaction.transaction.controller;


import com.Transaction.transaction.exception.ApiResponse;
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
    @PostMapping("/post")
    public ResponseEntity<Route12Dto> createRoute(@RequestBody Route12Dto route12Dto){
        Route12Dto route12Dto1=this.route12Service.createRoute(route12Dto);
        return new ResponseEntity<>(route12Dto1,HttpStatus.CREATED);
    }
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRoute(@PathVariable int id){
        this.route12Service.deleteRoute(id);
        return new ResponseEntity<>(new ApiResponse("Route is deleted by ADMIN",true,HttpStatus.OK),HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Route12Dto> updateRoute(@RequestBody Route12Dto route12Dto,@PathVariable int id){
        Route12Dto route12Dto1=this.route12Service.updateRoute(route12Dto,id);
        return new ResponseEntity<>(route12Dto1,HttpStatus.OK);
    }
    @PostMapping("/busStop/{id}/{id1}")
    public ResponseEntity<Route12Dto> createRouteWithBusStop(@RequestBody Route12Dto route12Dto,@PathVariable int
                                                             id, @PathVariable int id1){
        Route12Dto route12Dto1=this.route12Service.createRouteWithBusStop(route12Dto,id,id1);
        return new ResponseEntity<>(route12Dto1,HttpStatus.CREATED);
    }
    @GetMapping("source/{id}/destination/{id1}")
    public ResponseEntity<List<Route12Dto>> findShortestPath(@PathVariable int id,@PathVariable int id1){
        List<Route12Dto>  route12Dtos=this.route12Service.findShortestRoute(id,id1);
        return new ResponseEntity<>(route12Dtos,HttpStatus.OK);
    }
}
