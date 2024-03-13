package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.service.PaymentService;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Service
public class PaymentServiceImpl implements PaymentService {
    public JSONObject decodePaymentSignature(String encodedSignature) {
        String decodedSignature = decodeBase64(encodedSignature);
        return new JSONObject(Integer.parseInt(decodedSignature));
    }

    private String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
