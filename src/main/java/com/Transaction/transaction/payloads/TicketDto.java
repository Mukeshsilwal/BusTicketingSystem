package com.Transaction.transaction.payloads;


import com.Transaction.transaction.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private int ticketNo;
    private BookingTicketDto bookingTicket;

}
