package com.Transaction.transaction.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {
    private int id;
    private int noOfSeats;
    private String seatClass;
}
