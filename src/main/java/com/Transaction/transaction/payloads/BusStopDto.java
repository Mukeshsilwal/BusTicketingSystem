package com.Transaction.transaction.payloads;

import lombok.*;

import javax.validation.constraints.NotEmpty;

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
