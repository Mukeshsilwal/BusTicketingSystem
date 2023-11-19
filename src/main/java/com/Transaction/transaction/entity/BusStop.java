package com.Transaction.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int distance;
    private boolean visited;
    @OneToMany(mappedBy = "sourceBusStop",cascade = CascadeType.ALL)
    List<BusStopDistance> sourceBusStop1;
    @OneToMany(mappedBy = "destinationBusStop",cascade = CascadeType.ALL)
    List<BusStopDistance> destinationBusStop1;
    @OneToMany(mappedBy = "sourceBusStop",cascade = CascadeType.ALL)
    List<Route12> sourceRoutes;
    @OneToMany(mappedBy = "destinationBusStop",cascade = CascadeType.ALL)
    List<Route12> destinationRoutes;
}
