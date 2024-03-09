package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface BookingRequestRepo extends JpaRepository<BookingRequest,Integer> {

    // Example query method to cancel a BookingRequest by ticketId and email and date
    void deleteBySeatTicketTicketNoAndSeatTicketBookingTicketEmailAndSeatBusInfoDepartureDateTime(
            int ticketNo, String email, Date date);
}




