package com.Transaction.transaction.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusInfoDto {
    private int id;
    private String busName;
    private String busType;
    private double price;
    private Date time;
    private Route12Dto route12;
}
