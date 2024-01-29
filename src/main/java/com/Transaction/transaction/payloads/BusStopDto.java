package com.Transaction.transaction.payloads;

import com.Transaction.transaction.entity.BusStopDistance;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusStopDto {
    private int id;
    @NonNull
    @NotEmpty
    private String name;
    @NonNull
    @NotEmpty
    private int distance;
    @NonNull
    @NotEmpty
    private boolean visited;

}
