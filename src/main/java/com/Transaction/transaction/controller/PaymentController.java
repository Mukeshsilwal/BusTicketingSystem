package com.Transaction.transaction.controller;

import com.Transaction.transaction.model.PaymentData;
import com.Transaction.transaction.service.PaymentService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/decode")
    public ResponseEntity<PaymentData> decodePaymentSignature(@RequestParam String paymentRequest) {
        try {
            PaymentData paymentData = paymentService.decodePaymentSignature(paymentRequest);
            return new ResponseEntity<>(paymentData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

