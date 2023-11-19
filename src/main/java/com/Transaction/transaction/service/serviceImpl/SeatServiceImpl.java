package com.Transaction.transaction.service.serviceImpl;

import com.Transaction.transaction.entity.BookingTicket;
import com.Transaction.transaction.entity.BusInfo;
import com.Transaction.transaction.entity.Reservation;
import com.Transaction.transaction.entity.Seat;
import com.Transaction.transaction.exception.ResourceNotFoundException;
import com.Transaction.transaction.model.SeatType;
import com.Transaction.transaction.payloads.BookingTicketDto;
import com.Transaction.transaction.payloads.SeatDto;
import com.Transaction.transaction.repository.BookingRepo;
import com.Transaction.transaction.repository.BusInfoRepo;
import com.Transaction.transaction.repository.ReservationRepo;
import com.Transaction.transaction.repository.SeatRepo;
import com.Transaction.transaction.service.SeatReservation;
import com.Transaction.transaction.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {
    private final SeatRepo seatRepo;
    private final ModelMapper modelMapper;
    private final BusInfoRepo busInfoRepo;
    private final SeatReservation seatReservation;
    private final ReservationRepo reservationRepo;

    public SeatServiceImpl(SeatRepo seatRepo, ModelMapper modelMapper, BusInfoRepo busInfoRepo, SeatReservation seatReservation, ReservationRepo reservationRepo) {
        this.seatRepo = seatRepo;
        this.modelMapper = modelMapper;
        this.busInfoRepo = busInfoRepo;
        this.seatReservation = seatReservation;
        this.reservationRepo = reservationRepo;
    }

    @Override
    public SeatDto createSeat(SeatDto seatDto) {
        Seat seat=this.dtoToSeat(seatDto);
            Seat seat1=this.seatRepo.save(seat);
            return seatToDto(seat1);
    }

    @Override
    public SeatDto updateSeat(SeatDto seatDto, int id) {
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
            seat.setSeatName(seatDto.getSeatName());
            Seat seat1=this.seatRepo.save(seat);
            return seatToDto(seat1);
    }

    @Override
    public void deleteSeat(int id) {
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
        this.seatRepo.delete(seat);

    }

    @Override
    public SeatDto getSeatById(int id) {
        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
        return seatToDto(seat);
    }

    @Override
    public List<SeatDto> getAllSeat() {
        List<Seat> seats=this.seatRepo.findAll();
        return seats.stream().map(this::seatToDto).collect(Collectors.toList());
    }

    @Override
    public SeatDto createSeatForBus(SeatDto seatDto, int id) {
        Seat seat=this.dtoToSeat(seatDto);
       BusInfo busInfo=this.busInfoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BusInfo","id",id));
        seat.setBusInfo(busInfo);
        Seat seat1=this.seatRepo.save(seat);
        return seatToDto(seat1);
    }

//    @Override
//    public void deleteBookingSeat(int sId, int id) {
//        Seat seat=this.seatRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Seat","id",id));
//        BookingTicket bookingTicket=this.bookingRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookingTicket","id",id));
//        this.seatRepo.delete(seat);
//    }

    @Override
    public SeatType getSeatType(int id) {
        Seat seat = this.seatRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seat", "id", id));
        return seat.getSeatType();
    }

    @Override
    public List<SeatDto> reserveSeat(int numSeat, SeatType preference) {
        List<Seat> reservedSeats=this.seatReservation.reserveSeats(numSeat,preference);
        List<SeatDto> reservedSeatDtos=reservedSeats.stream().map(this::seatToDto).collect(Collectors.toList());
        Reservation reservation=new Reservation();
        reservation.setSeatReserve(reservedSeats);
        reservationRepo.save(reservation);
        return reservedSeatDtos;
    }

    public Seat dtoToSeat(SeatDto seatDto){
        return this.modelMapper.map(seatDto,Seat.class);
    }
    public SeatDto seatToDto(Seat seat){
        return this.modelMapper.map(seat,SeatDto.class);
    }
    public BookingTicket dtoToBooking(BookingTicketDto bookingTicketDto){
        return this.modelMapper.map(bookingTicketDto,BookingTicket.class);
    }
}
