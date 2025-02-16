package com.Transaction.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean reserved;
    private BigDecimal price;
    private String seatNumber;


    public void cancelRequest() {
        this.reserved = false;
    }

    @ManyToOne
    @JoinColumn(name = "fid", referencedColumnName = "id")
    private Bus bus;
    @OneToOne(mappedBy = "seat", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Ticket ticket;
    @OneToOne(mappedBy = "seat", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private BookingRequest booking;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
