package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface BookingRequestRepo extends JpaRepository<BookingRequest,Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE BookingRequest t " +
            "SET t.seatClass = 'CANCELLED' " +
            "WHERE t.seat.busInfo.route12.date = :date " +
            "AND t.seat.ticket.ticketNo = :ticketNo " +
            "AND t.seat.ticket.bookingTicket.email = :email")
    void cancelTicket(@Param("date") LocalDateTime date,
                      @Param("ticketNo") String ticketNo,
                      @Param("email") String email);
}
