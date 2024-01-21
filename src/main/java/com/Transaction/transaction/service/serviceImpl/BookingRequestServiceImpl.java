package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.Booking;
import com.Transaction.transaction.entity.BookingRequest;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.SeatsNotAvailableException;
import com.Transaction.transaction.model.ReservationResponse;
import com.Transaction.transaction.payloads.BookingRequestDto;
import com.Transaction.transaction.repository.BookingRequestRepo;
import com.Transaction.transaction.repository.BookingSeatsRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.BookingRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class BookingRequestServiceImpl implements BookingRequestService {
    private final SeatRepo seatRepo;
//    private final BookingRequestRepo requestRepo;
    private final ModelMapper modelMapper;
    private final BookingSeatsRepo bookingSeatsRepo;

    public BookingRequestServiceImpl(SeatRepo seatRepo, ModelMapper modelMapper, BookingSeatsRepo bookingSeatsRepo) {
        this.seatRepo = seatRepo;
        this.modelMapper = modelMapper;
        this.bookingSeatsRepo = bookingSeatsRepo;
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
                        Booking booking = new Booking();
//                        booking.setSeats(reservedSeats);
                        bookingSeatsRepo.save(booking);
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
