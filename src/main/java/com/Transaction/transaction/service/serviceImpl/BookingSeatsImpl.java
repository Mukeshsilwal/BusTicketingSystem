//package com.Transaction.transaction.service.serviceImpl;
//
//import com.Transaction.transaction.entity.Booking;
//import com.Transaction.transaction.payloads.BookingDto;
//import com.Transaction.transaction.repository.BookingSeatsRepo;
//import com.Transaction.transaction.service.BookingSeats;
//import org.modelmapper.ModelMapper;
//
//public class BookingSeatsImpl implements BookingSeats {
//    private final BookingSeatsRepo bookingSeatsRepo;
//    private final ModelMapper modelMapper;
//
//    public BookingSeatsImpl(BookingSeatsRepo bookingSeatsRepo, ModelMapper modelMapper) {
//        this.bookingSeatsRepo = bookingSeatsRepo;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public void bookSeats(BookingDto bookingDto) {
//        Booking booking=toBooking(bookingDto);
//        booking.
//
//    }
//    public Booking toBooking(BookingDto bookingDto){
//        return modelMapper.map(bookingDto,Booking.class);
//    }
//
//}
