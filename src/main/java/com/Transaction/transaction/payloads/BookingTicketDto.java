package com.Transaction.transaction.payloads;


import com.Transaction.transaction.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingTicketDto {
    private int bookingId;
    private Date bookingDate;
    private String fullName;
    private String email;
    private int seatNo;
    private UserDto user;
}
