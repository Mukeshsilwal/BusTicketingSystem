package com.Transaction.transaction.entity;

import com.Transaction.transaction.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Seat1111")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean reserved;
    private BigDecimal price;
    private String seatNumber;


    public void cancelRequest(){
        this.reserved=false;
    }

    @ManyToOne
    @JoinColumn(name = "fid",referencedColumnName = "id")
    private BusInfo busInfo;
    @OneToOne(mappedBy = "seat",fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Ticket ticket;
    @OneToOne(mappedBy = "seat",fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},orphanRemoval = true)
    private BookingRequest booking;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
