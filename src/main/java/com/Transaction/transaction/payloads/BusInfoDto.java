package com.Transaction.transaction.payloads;

import com.Transaction.transaction.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusInfoDto {
    private int id;
    private String busName;
    private String busType;
    private LocalDateTime departureDateTime;
    private LocalDate date;
    private Route12Dto route12;
    List<SeatDto> seats;
}
