package com.Transaction.transaction.algorithm;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;
@Configuration
public class DynamicPricingAlgorithm {

    // Basic pricing factors
    private static final double BASE_PRICE = 1500.0;
    private static final double PRICE_INCREMENT = 5.0;
    private static final double MAX_PRICE = 6000.0;

    // Pricing factors for dynamic adjustment
    private static final double DEMAND_FACTOR = 1.1;  // Increase price if demand is high
    private static final double TIME_FACTOR = 1.1;    // Increase price during peak hours

    public double calculateDynamicPrice(LocalDateTime departureTime, int availableSeats) {
        double dynamicPrice = BASE_PRICE;

        // Adjust price based on demand
        dynamicPrice *= DEMAND_FACTOR * calculateDemandFactor(availableSeats);

        // Adjust price based on time
        dynamicPrice *= TIME_FACTOR * calculateTimeFactor(departureTime);

        // Ensure the price does not exceed the maximum
        dynamicPrice = Math.min(dynamicPrice,MAX_PRICE);

        return dynamicPrice;
    }

    private double calculateDemandFactor(int availableSeats) {
        // Higher demand factor if fewer seats are available
        return 1.0 + (1.0 - (double) availableSeats / 33);
    }

    private double calculateTimeFactor(LocalDateTime departureTime) {
        // Higher time factor during peak hours (e.g., 7 AM - 9 AM)
        int peakStartHour = 7;
        int peakEndHour = 9;

        int departureHour = departureTime.getHour();
        System.out.println("Time in hour"+departureHour);
        if (departureHour >= peakStartHour && departureHour <= peakEndHour) {

            return 1.5;  // Increase price during peak hours
        } else {
            return 1.0;  // Normal pricing for other hours
        }
    }

}