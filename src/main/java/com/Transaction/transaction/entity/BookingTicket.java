package com.Transaction.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Book")
public class BookingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date bookingDate;
    private String fullName;
    private String email;
    private int seatNo;
    @OneToMany(mappedBy = "bookingTicket",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private Set<Ticket> ticket;
    @ManyToOne
    @JoinColumn(name = "id1")
    private User user;

}
