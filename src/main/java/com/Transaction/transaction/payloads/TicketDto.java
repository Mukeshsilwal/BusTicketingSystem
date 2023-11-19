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
public class TicketDto {
    private int ticketNo;
    private String passengerName;
    private String seatNo;
    private double price;
    private Date departureDate;
    private SeatDto seat;
    private BookingTicketDto bookingTicket;

}
