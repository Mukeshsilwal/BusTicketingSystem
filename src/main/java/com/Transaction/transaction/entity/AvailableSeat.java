package com.Transaction.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvailableSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int availableSeats;
}
