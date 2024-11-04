package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.model.PaymentData;
import com.Transaction.transaction.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

@Service
public class PaymentServiceImpl implements com.Transaction.transaction.service.PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public PaymentData decodePaymentSignature(String base64EncodedSignature) {
        try {
            String decodedSignature = decodeBase64(base64EncodedSignature);
            return new ObjectMapper().readValue(decodedSignature, PaymentData.class);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid Base64-encoded string: {}", e.getMessage());
            throw new RuntimeException("Invalid Base64-encoded string", e);
        } catch (Exception e) {
            logger.error("Error decoding payment signature: {}", e.getMessage());
            throw new RuntimeException("Error decoding payment signature", e);
        }
    }

    private String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.decodeBase64(encoded);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}

