package com.Transaction.transaction.controller;

import com.Transaction.transaction.entity.Ticket;
import com.Transaction.transaction.exception.ApiResponse;
import com.Transaction.transaction.payloads.TicketDto;
import com.Transaction.transaction.service.TicketPDFService;
import com.Transaction.transaction.service.TicketService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketPDFService ticketPDFService;
    private final TicketService ticketService;




    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateTicket(@RequestParam Integer ticketId) throws DocumentException {

        Ticket ticket = ticketService.getTicketById(ticketId);
        byte[] pdfData = ticketPDFService.generateTicketPDF(ticket);
        String userEmail =ticket.getBookingTicket().getEmail();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ticket.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ticketService.sendBookingConfirmationEmail(userEmail,pdfData);
        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
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
   @GetMapping("/get/{id}")
    public ResponseEntity<Ticket> getAllTicket(@PathVariable Integer id){
        Ticket ticketDto=this.ticketService.getTicketById(id);
        return new ResponseEntity<>(ticketDto,HttpStatus.OK);
   }

}
