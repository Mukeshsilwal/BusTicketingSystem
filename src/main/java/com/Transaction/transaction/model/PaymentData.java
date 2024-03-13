package com.Transaction.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentData {
    private String status;
    private String signature;
    private String transaction_code;
    private int total_amount;
    private String transaction_uuid;
    private String product_code;
    private String success_url;
    private String signed_field_names;
}


