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
@Table(name="Bus")
public class BusInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String busName;
    private String busType;
    private double price;
    private Date time;
//    @OneToMany(mappedBy = "busInfo",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    Set<Seat> seats;
    @ManyToOne
    @JoinColumn(name = "fid")
    private Route12 route12;
}
