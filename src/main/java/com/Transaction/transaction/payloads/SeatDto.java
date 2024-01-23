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
    private float price;
    private boolean reserved;
    private char row1;
    private int col1;
    private String seatNumber;
    private String zone;
    private SeatType seatType;
    private BookingRequestDto booking;
    private BusInfoDto busInfo;


}
