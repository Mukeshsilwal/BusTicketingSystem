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
@NoArgsConstructor
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int distance;
    private boolean visited;
    @OneToMany(mappedBy = "sourceBusStop",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},orphanRemoval = true)
    List<Route12> sourceRoutes;
    @OneToMany(mappedBy = "destinationBusStop",cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},orphanRemoval = true)
    List<Route12> destinationRoutes;
}
