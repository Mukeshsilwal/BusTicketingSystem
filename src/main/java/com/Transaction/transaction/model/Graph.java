package com.Transaction.transaction.model;

import com.Transaction.transaction.entity.BusStop;
import com.Transaction.transaction.entity.Route12;
import com.Transaction.transaction.payloads.BusStopDto;
import com.Transaction.transaction.payloads.Route12Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Graph {
    List<BusStop> busStops;
    List<Route12> route12s;

    public void resetBusDistance(){
        for(BusStop busStop:busStops){
            busStop.setDistance(Integer.MAX_VALUE);
            busStop.setVisited(false);

        }
    }
}
