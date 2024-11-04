package com.Transaction.transaction.service;

import com.Transaction.transaction.model.PaymentData;
import net.minidev.json.JSONObject;

public interface PaymentService {
    PaymentData decodePaymentSignature(String paymentRequest);
}
