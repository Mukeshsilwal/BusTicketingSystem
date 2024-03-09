package com.Transaction.transaction.service;


import com.Transaction.transaction.entity.Ticket;
import com.Transaction.transaction.payloads.TicketDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class TicketPDFService {

    public byte[] generateTicketPDF(Ticket ticket) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();
        document.add(new Paragraph("Ticket Information"));
        document.add(new Paragraph("Ticket Number: " + ticket.getTicketNo()));
        document.add(new Paragraph("Passenger FullName: " + ticket.getBookingTicket().getFullName()));
        document.add(new Paragraph("Seat Number: " + ticket.getSeatNo()));
        document.add(new Paragraph("From: " + ticket.getSeat().getBusInfo().getRoute12().getSourceBusStop().getName()));
        document.add(new Paragraph("Destination: " + ticket.getSeat().getBusInfo().getRoute12().getDestinationBusStop().getName()));
        document.add(new Paragraph("Departure Date: " + ticket.getSeat().getBusInfo().getDepartureDateTime()));
        document.add(new Paragraph("Price: " + ticket.getSeat().getPrice()));
        document.close();

        return outputStream.toByteArray();
    }
}

