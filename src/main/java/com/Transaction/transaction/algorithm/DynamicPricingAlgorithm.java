package com.Transaction.transaction.algorithm;

import com.Transaction.transaction.entity.Bus;
import com.Transaction.transaction.repository.SeatRepo;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Configuration
public class DynamicPricingAlgorithm {
    private final SeatRepo seatRepo;

    private static final BigDecimal HIGH_DEMAND_FACTOR = new BigDecimal("1.25");
    private static final BigDecimal LOW_DEMAND_FACTOR = new BigDecimal("0.8");
    private static final BigDecimal TIME_FACTOR = new BigDecimal("1.1");

    public DynamicPricingAlgorithm(SeatRepo seatRepo) {
        this.seatRepo = seatRepo;
    }

    public BigDecimal calculateDynamicPrice(int availableSeats, LocalDate date, Bus busInfo) {
        int totalSeats = seatRepo.countByBus(busInfo);
        BigDecimal dynamicPrice = busInfo.getBasePrice();
        if (totalSeats != 0) {
            if (availableSeats <= 10) {
                dynamicPrice = dynamicPrice.multiply(HIGH_DEMAND_FACTOR.multiply(calculateDemandFactor(availableSeats, totalSeats)));
            } else {
                dynamicPrice = dynamicPrice.multiply(LOW_DEMAND_FACTOR.multiply(calculateDemandFactor(availableSeats, totalSeats)));
            }
        } else {
            return dynamicPrice;
        }

        dynamicPrice = dynamicPrice.multiply(TIME_FACTOR.multiply(calculateTimeFactor(date)));

        dynamicPrice = dynamicPrice.min(busInfo.getMaxPrice());
        dynamicPrice = dynamicPrice.setScale(2, RoundingMode.HALF_UP);

        return dynamicPrice;
    }

    private BigDecimal calculateDemandFactor(int availableSeats, int totalSeats) {
        return BigDecimal.valueOf(1.0).add(BigDecimal.valueOf(1.0).subtract(BigDecimal.valueOf((double) availableSeats / totalSeats)));
    }

    private BigDecimal calculateTimeFactor(LocalDate date) {
        int daysDifference = LocalDate.now().until(date).getDays();
        System.out.println("Days Different :" + daysDifference);
        if (daysDifference <= 2) {
            return BigDecimal.valueOf(1.2);
        } else {
            return BigDecimal.valueOf(1.0);
        }
    }
}
