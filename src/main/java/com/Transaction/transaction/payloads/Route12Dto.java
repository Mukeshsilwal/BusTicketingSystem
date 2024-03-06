package com.Transaction.transaction.payloads;


import com.Transaction.transaction.entity.BusStop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route12Dto {
    private int id;
    private LocalDateTime date;
    private BusStopDto sourceBusStop;
    private BusStopDto destinationBusStop;
}
