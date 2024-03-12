package com.Transaction.transaction.controller;

import com.Transaction.transaction.payloads.BusInfoDto;
import com.Transaction.transaction.service.BusInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusInfoController {
    private final BusInfoService busInfoService;
    @GetMapping("/route")
    public ResponseEntity<List<BusInfoDto>> getAllRoute(){
        List<BusInfoDto>  busInfo=this.busInfoService.getAllBusInfo();
        return new ResponseEntity<>(busInfo,HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<BusInfoDto>> search(@RequestParam String source, @RequestParam String destination) throws ParseException {
           List<BusInfoDto> busInfoDtos = this.busInfoService.getBusByRoute(source, destination);
           return new ResponseEntity<>(busInfoDtos,HttpStatus.OK);

    }
}
