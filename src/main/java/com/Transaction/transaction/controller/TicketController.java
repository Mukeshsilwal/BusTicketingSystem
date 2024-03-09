package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.BookingTicketDto;
import com.Transaction.transaction.payloads.TicketDto;
import com.Transaction.transaction.service.BookingRequestService;
import com.Transaction.transaction.service.TicketPDFService;
import com.Transaction.transaction.service.TicketService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketPDFService ticketPDFService;
    private final TicketService ticketService;




    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateTicket(@RequestParam Integer ticketId) throws DocumentException {

        TicketDto ticket = ticketService.getTicketById(ticketId);
        byte[] pdfData = ticketPDFService.generateTicketPDF(ticket);
        String userEmail =ticket.getBookingTicket().getEmail();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ticket.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ticketService.sendBookingConfirmationEmail(userEmail,pdfData);
        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto){
        TicketDto ticketDto1 =this.ticketService.createTicket(ticketDto);
        return new ResponseEntity<>(ticketDto1, HttpStatus.CREATED);
    }
    @PutMapping("/put/{id}")
    public ResponseEntity<TicketDto> updateTicket(@RequestBody TicketDto ticketDto,@PathVariable Integer id){
        TicketDto ticketDto1=this.ticketService.updateTicket(ticketDto,id);
        return new ResponseEntity<>(ticketDto1,HttpStatus.OK);
    }
    @PostMapping("/seat/{id}/book/{bId}")
    public ResponseEntity<TicketDto> createTicketForSeat(@RequestBody TicketDto ticketDto,@PathVariable Integer id,
                                                         @PathVariable Integer bId){
        TicketDto ticketDto1=this.ticketService.createSeatWithTicket(ticketDto,id,bId);
        return new ResponseEntity<>(ticketDto1,HttpStatus.CREATED);
    }
    @DeleteMapping("/ticket/{tId}")
    public ResponseEntity<ApiResponse> deleteTicketOfSeat(@PathVariable Integer tId){
        this.ticketService.deleteSeatWithTicket(tId);
        return new ResponseEntity<>(new ApiResponse("Tickete Has Been Removed successfully",true,HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping("/book/{id}")
    public ResponseEntity<TicketDto> createTicketForBooking(@RequestBody TicketDto ticketDto,@PathVariable Integer id){
        TicketDto ticketDto1=this.ticketService.createTicketWithBooking(ticketDto,id);
        return new ResponseEntity<>(ticketDto1,HttpStatus.CREATED);
    }
    @PutMapping("/book/{id}")
    public ResponseEntity<TicketDto> updateTicketWithBooking(@RequestBody TicketDto ticketDto,@PathVariable Integer id){
        TicketDto ticketDto1=this.ticketService.updateTicketWithBooking(ticketDto,id);
        return new ResponseEntity<>(ticketDto1,HttpStatus.OK);
    }
   @GetMapping("/get/{id}")
    public ResponseEntity<TicketDto> getAllTicket(@PathVariable Integer id){
        TicketDto ticketDto=this.ticketService.getTicketById(id);
        return new ResponseEntity<>(ticketDto,HttpStatus.OK);
   }

}
