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
    private int seatId;
    private String seatName;
    private float price;
    private char row1;
    private int col1;
    private SeatType seatType;
    private BusInfoDto busInfo;


}
