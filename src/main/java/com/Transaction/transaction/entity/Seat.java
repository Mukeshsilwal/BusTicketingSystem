package com.Transaction.transaction.entity;

import com.Transaction.transaction.model.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Seat1111")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;
    private String seatName;
    private char row1;
    private int col1;
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    private float price;

    public Seat(char row1, int col1, SeatType seatType) {
        this.row1 = row1;
        this.col1 = col1;
        this.seatType = seatType;
    }

    @ManyToOne
    @JoinColumn(name = "fid",referencedColumnName = "id")
    private BusInfo busInfo;
    @OneToOne(mappedBy = "seat",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Ticket ticket;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reserve_id",referencedColumnName = "id")
    private Reservation reservation;

}
