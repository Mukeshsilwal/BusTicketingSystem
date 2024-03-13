package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.service.PaymentService;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public JSONObject decodePaymentSignature(String encodedSignature) {
        try {
            String decodedSignature = decodeBase64(encodedSignature);
            return new JSONObject(Integer.parseInt(decodedSignature));
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
