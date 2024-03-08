package com.Transaction.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPreferences {
    private boolean windowSeatPreferred;
    private boolean aisleSeatPreferred;
    private boolean middleSeatPreferred;
}