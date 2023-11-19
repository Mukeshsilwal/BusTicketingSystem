package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.model.SeatType;
import com.Transaction.transaction.payloads.ReservationDto;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/get")
    public ResponseEntity<List<SeatDto>> getAllSeat(){
        List<SeatDto> seatDto=this.seatService.getAllSeat();
        return new ResponseEntity<>(seatDto, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getSeatById(@PathVariable Integer id){
        SeatDto seatDto=this.seatService.getSeatById(id);
        return new ResponseEntity<>(seatDto,HttpStatus.OK);
    }
    @PostMapping("/post")
    public ResponseEntity<SeatDto> createSeat(@RequestBody SeatDto seatDto){
        SeatDto seatDto1=this.seatService.createSeat(seatDto);
        return new ResponseEntity<>(seatDto1,HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<SeatDto> updateSeat(@RequestBody SeatDto seatDto,@PathVariable Integer id){
        SeatDto seatDto1=this.seatService.updateSeat(seatDto,id);
        return new ResponseEntity<>(seatDto1,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSeat(@PathVariable Integer id){
        this.seatService.deleteSeat(id);
        return new ResponseEntity<>(new ApiResponse("Seat Has Been Deleted Successfully",true,HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping("/book/{id}")
    public ResponseEntity<SeatDto> createSeatForBus(@RequestBody SeatDto seatDto,@PathVariable Integer id){
        SeatDto seatDto1=this.seatService.createSeatForBus(seatDto,id);
        return new ResponseEntity<>(seatDto1,HttpStatus.CREATED);
    }
//    @DeleteMapping("/seat/{sId}/book/{id}")
//    public ResponseEntity<ApiResponse> deleteBookedSeat(@PathVariable Integer sId,@PathVariable Integer id){
//        this.seatService.deleteBookingSeat(sId,id);
//        return new ResponseEntity<>(new ApiResponse("Booked Seat Has Been Removed",true,HttpStatus.OK),HttpStatus.OK);
//    }
    @GetMapping("/seatType/{id}")
    public ResponseEntity<SeatType> getSeatType(@PathVariable Integer id) {
        SeatType seatType = this.seatService.getSeatType(id);
        return new ResponseEntity<>(seatType, HttpStatus.OK);


    }

}
