package com.Transaction.transaction.payloads;

import com.Transaction.transaction.entity.BusStopDistance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusStopDto {
    private int id;
    private String name;
    private int distance;
    private boolean visited;

//    private List<BusStopDistanceDto> sourceBusStop1;
//    private List<BusStopDistanceDto> destinationBusStop1;

}
