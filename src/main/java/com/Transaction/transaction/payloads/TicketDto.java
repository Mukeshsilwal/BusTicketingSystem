package com.Transaction.transaction.payloads;


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
    @NotBlank(message = "Name cannot be blank")
    private String passengerName;
    private String seatNo;
    @NotBlank(message = "Price field cannot be blank")
    private double price;
    @NotBlank(message = "Name cannot be blank")
    private Date departureDate;
    private SeatDto seat;
    private BookingTicketDto bookingTicket;

}
