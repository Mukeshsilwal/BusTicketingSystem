package com.Transaction.transaction.algorithm;

import com.Transaction.transaction.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class FirstInFirstOut {
    private final Queue<Seat> seatQueue=new LinkedList<>();

    public void enqueue(Seat seat){
        seatQueue.add(seat);
    }
    public void dequeue(Seat seat){
        seatQueue.poll();
    }
    public Seat peek() {
        return seatQueue.peek();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return seatQueue.isEmpty();
    }

    // Get the size of the queue
    public int size() {
        return seatQueue.size();
    }
}
