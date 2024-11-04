package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.service.SignatureService;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SignatureServiceImpl implements SignatureService {
    @Override
    public String createSig(String str) {
        try {
            String secret = "8gBm/:&EnhH.1/q";
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKey);
            byte[] hashedBytes = sha256Hmac.doFinal(str.getBytes(StandardCharsets.UTF_8));
            // Encode the bytes to Base64
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
