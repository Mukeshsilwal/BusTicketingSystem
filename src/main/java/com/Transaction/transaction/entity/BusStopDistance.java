package com.Transaction.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusStopDistance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_bus_stop_id")
    private BusStop sourceBusStop;

    @ManyToOne
    @JoinColumn(name = "destination_bus_stop_id")
    private BusStop destinationBusStop;

    private double distance;
}
