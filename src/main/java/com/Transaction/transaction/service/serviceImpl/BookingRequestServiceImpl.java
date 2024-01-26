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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public  class BookingRequestServiceImpl implements BookingRequestService {
    private final SeatRepo seatRepo;
    private final ModelMapper modelMapper;
    private final BookingRequestRepo requestRepo;

    public BookingRequestServiceImpl(SeatRepo seatRepo, ModelMapper modelMapper,BookingRequestRepo requestRepo) {
        this.seatRepo = seatRepo;
        this.modelMapper = modelMapper;
        this.requestRepo = requestRepo;
    }

    @Override
    public ReservationResponse rserveSeats(BookingRequestDto requestDto) {
        BookingRequest request=toRequest(requestDto);
        if (areSeatsAvailable(request)) {
            // Reserve seats and confirm the booking
            reserveSeatsAndUpdateDatabase(request);
            return new ReservationResponse(true, "Booking confirmed");
        } else {
            // Provide alternative options or notify the user of unavailability
            return new ReservationResponse(false, "Seats not available");
        }
    }




    @Transactional
    @Override
    public void cancelReservation(int bookingId) {
        Seat seat=this.seatRepo.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("Seat","bookingId",bookingId));
        BookingRequest booking = seat.getBooking();
        if (booking!=null) {
           seat.setBooking(null);
           seat.setReserved(false);
            seatRepo.save(seat);

            requestRepo.delete(booking);

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
