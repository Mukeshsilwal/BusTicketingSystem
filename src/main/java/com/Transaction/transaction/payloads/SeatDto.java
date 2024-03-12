package com.Transaction.transaction.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    private int id;
    private boolean reserved;
    private String seatNumber;
    private BigDecimal price;
}
