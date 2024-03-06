package com.Transaction.transaction.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusInfoDto {
    private int id;
    private String busName;
    private String busType;
    private Date departureDateTime;
    private double price;
    private Route12Dto route12;
}
