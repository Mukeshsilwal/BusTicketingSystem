package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.BookingRequest;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.BookingNotFoundException;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.model.ReservationResponse;
import com.Transaction.transaction.payloads.BookingRequestDto;
import com.Transaction.transaction.repository.BookingRequestRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.BookingRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public  class BookingRequestServiceImpl implements BookingRequestService {
    private final SeatRepo seatRepo;
    private final ModelMapper modelMapper;
    private final BookingRequestRepo requestRepo;

    public BookingRequestServiceImpl(SeatRepo seatRepo, ModelMapper modelMapper, BookingRequestRepo requestRepo) {
        this.seatRepo = seatRepo;
        this.modelMapper = modelMapper;
        this.requestRepo = requestRepo;
    }

    @Override
    public ReservationResponse rserveSeat(BookingRequestDto requestDto,int seatId) {
        BookingRequest request = toRequest(requestDto);

        // Retrieve the seat by its ID
        Seat seat = seatRepo.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "seatId", seatId));

        if (isSeatAvailable(seat)) {
            // Reserve the seat and confirm the booking
            request.setSeat(seat);
            reserveSeatAndUpdateDatabase(request);
            requestRepo.save(request);
            return new ReservationResponse(true, "Booking confirmed");
        } else {
            // Provide alternative options or notify the user of unavailability
            return new ReservationResponse(false, "Seat not available");
        }
    }

    private boolean isSeatAvailable(Seat seat) {
        // Check if the seat is available for reservation (add your logic here)
        return !seat.isReserved();
    }

    private void reserveSeatAndUpdateDatabase(BookingRequest request) {
        // Perform seat reservation logic and update the database
        Seat seat = request.getSeat();
        seat.setReserved(true);
        seatRepo.save(seat);
    }
    @Transactional
    @Override
    public void cancelReservation(String email, int ticketNo,  int bookingId) {
        Seat seat = seatRepo.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Seat", "bookingId", bookingId));
        BookingRequest booking = seat.getBooking();

        if (booking != null) {
            // Cancel the ticket using the repository method
            requestRepo.deleteBySeatTicketTicketNoAndSeatTicketBookingTicketEmail(
                    ticketNo,
                    email);

            // Clear the association from Seat and update
            seat.setBooking(null);
            seat.setReserved(false);
            seatRepo.save(seat);
        } else {
            // Handle the case where the booking with the given ID is not found
            throw new BookingNotFoundException("Booking not found for id: " + bookingId);
        }
    }
    public BookingRequest toRequest(BookingRequestDto bookingDto){
        return modelMapper.map(bookingDto,BookingRequest.class);
    }
}
