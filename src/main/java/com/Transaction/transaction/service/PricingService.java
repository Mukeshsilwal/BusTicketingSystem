package com.Transaction.transaction.service;

import com.Transaction.transaction.payloads.AvailableDto;

public interface PricingService {
    AvailableDto createAvailableSeats(AvailableDto  availableDto);
    public double calculateDemandFactor(int seatId);
    AvailableDto updateSeat(AvailableDto availableDto,int id);
}
