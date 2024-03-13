package com.Transaction.transaction.service;

import net.minidev.json.JSONObject;

public interface PaymentService {
    JSONObject decodePaymentSignature(String encodedSignature);
}
