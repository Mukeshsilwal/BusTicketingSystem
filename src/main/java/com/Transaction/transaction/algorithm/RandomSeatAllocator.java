package com.Transaction.transaction.algorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class RandomSeatAllocator {
    List<Integer> availableSeats;
    private Random random;
    public RandomSeatAllocator(int totalSeats){
        availableSeats=new ArrayList<>();
        for(int seatNumber=1;seatNumber<=totalSeats;seatNumber++){
            availableSeats.add(seatNumber);
        }
        random=new Random();
    }
    public int allocateSeat() {
        if (availableSeats.isEmpty()) {
            return -1;
        }

        // Shuffle the list of available seats
        Collections.shuffle(availableSeats);

        // Get the first seat from the shuffled list
        int allocatedSeat = availableSeats.get(0);

        // Remove the allocated seat from the list
        availableSeats.remove(Integer.valueOf(allocatedSeat));

        return allocatedSeat;
    }
}

