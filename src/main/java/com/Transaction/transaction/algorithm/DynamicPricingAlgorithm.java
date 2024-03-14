package com.Transaction.transaction.algorithm;

import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class DynamicPricingAlgorithm {

    // Basic pricing factors
    private static final BigDecimal BASE_PRICE = new BigDecimal("1800.0");
    private static final BigDecimal MAX_PRICE = new BigDecimal("2500.0");

    // Pricing factors for dynamic adjustment
    private static final BigDecimal HIGH_DEMAND_FACTOR = new BigDecimal("1.2");          // Increase price if demand is high
    private static final BigDecimal LOW_DEMAND_FACTOR = new BigDecimal("0.8");      // Decrease price if demand is low
    private static final BigDecimal TIME_FACTOR = new BigDecimal("1.1");            // Increase price during peak hours

    public BigDecimal calculateDynamicPrice(LocalDateTime departureTime, int availableSeats) {
        BigDecimal dynamicPrice = BASE_PRICE;

        // Adjust price based on demand
        if (availableSeats <= 10) {
            // Apply the low demand factor if available seats are low
            dynamicPrice = dynamicPrice.multiply(HIGH_DEMAND_FACTOR.multiply(calculateDemandFactor(availableSeats)));
        } else {
            dynamicPrice = dynamicPrice.multiply(LOW_DEMAND_FACTOR.multiply(calculateDemandFactor(availableSeats)));
        }

        // Adjust price based on time
        dynamicPrice = dynamicPrice.multiply(TIME_FACTOR.multiply(calculateTimeFactor(departureTime)));

        // Ensure the price does not exceed the maximum
        dynamicPrice = dynamicPrice.min(MAX_PRICE);
        dynamicPrice = dynamicPrice.setScale(2, RoundingMode.HALF_UP);

        return dynamicPrice;
    }

    private BigDecimal calculateDemandFactor(int availableSeats) {
        // Higher demand factor if fewer seats are available
        return BigDecimal.valueOf(1.0).add(BigDecimal.valueOf(1.0).subtract(BigDecimal.valueOf((double) availableSeats / 33)));
    }

    private BigDecimal calculateTimeFactor(LocalDateTime departureTime) {
        // Higher time factor during peak hours (e.g., 7 AM - 9 AM)
        int peakStartHour = 7;
        int peakEndHour = 9;

        int departureHour = departureTime.getHour();
        System.out.println("Time in hour" + departureHour);
        if (departureHour >= peakStartHour && departureHour <= peakEndHour) {
            return BigDecimal.valueOf(1.5);  // Increase price during peak hours
        } else {
            return BigDecimal.valueOf(1.0);  // Normal pricing for other hours
        }
    }
}
