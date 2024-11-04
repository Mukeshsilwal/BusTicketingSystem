package com.Transaction.transaction.service.serviceImpl;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpGeneratorService {
    private static final int OTP_LENGTH = 6;

    public  String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
