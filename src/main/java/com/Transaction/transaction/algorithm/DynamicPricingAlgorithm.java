package com.Transaction.transaction.algorithm;

import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.repository.SeatRepo;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class DynamicPricingAlgorithm {
    private final SeatRepo seatRepo;

    // Basic pricing factors
    private static final BigDecimal BASE_PRICE = new BigDecimal("2000.0");
    private static final BigDecimal MAX_PRICE = new BigDecimal("2500.0");

    // Pricing factors for dynamic adjustment
    private static final BigDecimal HIGH_DEMAND_FACTOR = new BigDecimal("1.25");          // Increase price if demand is high
    private static final BigDecimal LOW_DEMAND_FACTOR = new BigDecimal("1.0");      // Decrease price if demand is low
    private static final BigDecimal TIME_FACTOR = new BigDecimal("1.1");            // Increase price during peak hours

    public DynamicPricingAlgorithm(SeatRepo seatRepo) {
        this.seatRepo = seatRepo;
    }

    public BigDecimal calculateDynamicPrice(int availableSeats, LocalDate date, BusInfo busInfo) {
        int totalSeats=seatRepo.countByBusInfo(busInfo);
        BigDecimal dynamicPrice = BASE_PRICE;
if(totalSeats!=0) {
    // Adjust price based on demand
    if (availableSeats <= 10) {
        // Apply the low demand factor if available seats are low
        dynamicPrice = dynamicPrice.multiply(HIGH_DEMAND_FACTOR.multiply(calculateDemandFactor(availableSeats, totalSeats)));
    } else {
        dynamicPrice = dynamicPrice.multiply(LOW_DEMAND_FACTOR.multiply(calculateDemandFactor(availableSeats, totalSeats)));
    }
}
else{
    return BASE_PRICE;
}

        // Adjust price based on time
        dynamicPrice = dynamicPrice.multiply(TIME_FACTOR.multiply(calculateTimeFactor(date)));

        // Ensure the price does not exceed the maximum
        dynamicPrice = dynamicPrice.min(MAX_PRICE);
        dynamicPrice = dynamicPrice.setScale(2, RoundingMode.HALF_UP);

        return dynamicPrice;
    }

    private BigDecimal calculateDemandFactor(int availableSeats,int totalSeats) {
        // Higher demand factor if fewer seats are available
        return BigDecimal.valueOf(1.0).add(BigDecimal.valueOf(1.0).subtract(BigDecimal.valueOf((double) availableSeats /totalSeats )));
    }

    private BigDecimal calculateTimeFactor(LocalDate date) {
        int daysDifference =LocalDate.now().until(date).getDays();
        System.out.println("Days Different :"+daysDifference);
        if (daysDifference<=2) {
            return BigDecimal.valueOf(1.2);  // Increase price during Last Day
        } else {
            return BigDecimal.valueOf(1.0);  // Normal pricing for other Day
        }
    }
}
