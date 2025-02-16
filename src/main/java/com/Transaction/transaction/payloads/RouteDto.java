package com.Transaction.transaction.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {
    private int id;
    private BusStopDto sourceBusStop;
    private BusStopDto destinationBusStop;
}
