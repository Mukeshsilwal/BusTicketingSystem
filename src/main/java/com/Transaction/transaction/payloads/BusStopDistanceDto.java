package com.Transaction.transaction.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusStopDistanceDto {
    private long id;
    private double distance;
    private BusStopDto sourceBusStop;
    private BusStopDto destinationBusStop;
}
