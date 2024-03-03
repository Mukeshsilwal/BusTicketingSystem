package com.Transaction.transaction.controller;

import com.Transaction.transaction.payloads.AvailableDto;
import com.Transaction.transaction.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class DynamicPricingController {
    private final PricingService pricingService;
    @PostMapping("/post")
    public ResponseEntity<AvailableDto>  createAvailable(@RequestBody AvailableDto availableDto){
        AvailableDto availableDto1=this.pricingService.createAvailableSeats(availableDto);
                return new ResponseEntity<>(availableDto1, HttpStatus.CREATED);
    }
    @GetMapping("/dynamicPrice/{id}")
    public ResponseEntity<Double> getDynamicPrice(@PathVariable int id){
        double price=pricingService.calculateDemandFactor(id);
        return new ResponseEntity<>(price,HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<AvailableDto> updateSeatAvailable(@RequestBody AvailableDto availableDto,@PathVariable int id){
        AvailableDto availableDto1=this.pricingService.updateSeat(availableDto,id);
        return new ResponseEntity<>(availableDto1,HttpStatus.OK);
    }
}
