package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.BookingRequest;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.BookingNotFoundException;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.exception.SeatOrBookingRequestNotFoundException;
import com.Transaction.transaction.exception.SeatsNotAvailableException;
import com.Transaction.transaction.model.ReservationResponse;
import com.Transaction.transaction.payloads.BookingRequestDto;
import com.Transaction.transaction.repository.BookingRequestRepo;
import com.Transaction.transaction.repository.BookingSeatsRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.BookingRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public  class BookingRequestServiceImpl implements BookingRequestService {
    private final SeatRepo seatRepo;
    private final ModelMapper modelMapper;
    private final BookingRequestRepo requestRepo;
    private final EmailService emailService;

    public BookingRequestServiceImpl(SeatRepo seatRepo, ModelMapper modelMapper, BookingRequestRepo requestRepo, EmailService emailService) {
        this.seatRepo = seatRepo;
        this.modelMapper = modelMapper;
        this.requestRepo = requestRepo;
        this.emailService = emailService;
    }

    @Override
    public ReservationResponse rserveSeat(BookingRequestDto requestDto,int seatId) {
        BookingRequest request = toRequest(requestDto);

        // Retrieve the seat by its ID
        Seat seat = seatRepo.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat", "seatId", seatId));

        request.setSeat(seat);

        if (isSeatAvailable(seat)) {
            // Reserve the seat and confirm the booking
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
    public void cancelReservation(String email, int ticketNo, Date date, int bookingId) {
        Seat seat = seatRepo.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Seat", "bookingId", bookingId));
        BookingRequest booking = seat.getBooking();

        if (booking != null) {
            // Cancel the ticket using the repository method
            requestRepo.deleteBySeatTicketTicketNoAndSeatTicketBookingTicketEmailAndSeatBusInfoDepartureDateTime(
                    ticketNo,
                    email,date);

            // Clear the association from Seat and update
            seat.setBooking(null);
            seat.setReserved(false);
            seatRepo.save(seat);
        } else {
            // Handle the case where the booking with the given ID is not found
            throw new BookingNotFoundException("Booking not found for id: " + bookingId);
        }
    }

    @Override
    public void associateSeatWithBooking(int seatId, int bookingRequestId) {
        Optional<Seat> optionalSeat = seatRepo.findById(seatId);
        Optional<BookingRequest> optionalBookingRequest = requestRepo.findById(bookingRequestId);

        if (optionalSeat.isPresent() && optionalBookingRequest.isPresent()) {
            Seat seat = optionalSeat.get();
            BookingRequest bookingRequest = optionalBookingRequest.get();

            // Associate the seat with the booking request
            seat.setBooking(bookingRequest);

            // Save the updated seat in the database
            seatRepo.save(seat);
        } else {
            // Handle the case where the seat or booking request is not found
            throw new SeatOrBookingRequestNotFoundException("Seat or BookingRequest not found");
        }
    }

    private boolean areSeatsAvailable(BookingRequest request) {
        long count1=seatRepo.count();
        long count = request.getNoOfSeats();
        return count <= count1;
    }

    private void reserveSeatsAndUpdateDatabase(BookingRequest request) {

            if(areSeatsAvailable(request)){
                int count= request.getNoOfSeats();
                List<Seat> reservedSeats=seatRepo.findFirstNByReservedFalse();
                if (reservedSeats.size() >= count) {
                    // Check if all the seats are not already reserved
                    boolean allSeatsNotReserved = reservedSeats.stream().noneMatch(Seat::isReserved);

                    if (allSeatsNotReserved) {
                        reservedSeats = reservedSeats.subList(0, count);
                        reservedSeats.forEach(seat -> seat.setReserved(true));
                        seatRepo.saveAll(reservedSeats);
                        BookingRequest booking = new BookingRequest();
                        requestRepo.save(booking);
                    }
                    else{
                        throw new SeatsNotAvailableException("Some of the requested seats are already reserved");
                    }
                }
                else {

                    throw new SeatsNotAvailableException("Requested seats are not available");
                }

            }
            else {
                throw new SeatsNotAvailableException("Requested seats are not available.");
            }
        }
    public BookingRequest toRequest(BookingRequestDto bookingDto){
        return modelMapper.map(bookingDto,BookingRequest.class);
    }
}
