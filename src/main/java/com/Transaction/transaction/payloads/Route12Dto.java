package com.Transaction.transaction.payloads;


import com.Transaction.transaction.entity.BusStop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route12Dto {
    private int id;
    private String fro;
    private String too;
    private Date date;
    private int weight;
    private BusStopDto sourceBusStop;
    private BusStopDto destinationBusStop;
}
