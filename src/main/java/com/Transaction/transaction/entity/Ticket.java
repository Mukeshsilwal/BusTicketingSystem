package com.Transaction.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketNo;
    private String passengerName;
    private String seatNo;
    private Date departureDate;
    private double price;
    @OneToOne
    @JoinColumn(name="seat_Id",referencedColumnName = "seatId")
    private Seat seat;
    @ManyToOne
    @JoinColumn(name="booking_id",referencedColumnName = "bookingId")
    private BookingTicket bookingTicket;

}
