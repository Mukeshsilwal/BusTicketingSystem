package com.Transaction.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Bus")
public class    Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String busName;
    private String busType;
    private LocalDateTime departureDateTime;
    private LocalDate date;
    private BigDecimal basePrice;
    private BigDecimal maxPrice;
    @ManyToOne
    @JoinColumn(name = "fid")
    private Route route;
    @OneToMany(mappedBy = "bus", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    List<Seat> seats;
}
