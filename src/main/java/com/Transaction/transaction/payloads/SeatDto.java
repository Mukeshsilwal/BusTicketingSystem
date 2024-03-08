package com.Transaction.transaction.payloads;


import com.Transaction.transaction.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    private int id;
    private String seatName;
    private boolean reserved;
    private String seatNumber;
    private int capacity;
    private double price;
    private SeatType seatType;
//    private BusInfoDto busInfo;


}
