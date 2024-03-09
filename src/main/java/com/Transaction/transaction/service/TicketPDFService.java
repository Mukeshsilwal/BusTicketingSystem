package com.Transaction.transaction.service;


import com.Transaction.transaction.entity.Ticket;
import com.Transaction.transaction.payloads.TicketDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLUE);
        Paragraph title = new Paragraph("Ticket Information", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Ticket details box
        PdfPTable detailsBox = new PdfPTable(1);
        detailsBox.setWidthPercentage(100);
        detailsBox.getDefaultCell().setBorder(Rectangle.BOX);
        detailsBox.setSpacingAfter(10f);

        // Ticket details
        Font labelFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
        Font valueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);

        addTableCell(detailsBox, "Ticket Number:", String.valueOf(ticket.getTicketNo()), labelFont, valueFont);
        addTableCell(detailsBox, "Passenger FullName:", ticket.getBookingTicket().getFullName(), labelFont, valueFont);
        addTableCell(detailsBox, "Seat Number:", ticket.getSeatNo(), labelFont, valueFont);
        addTableCell(detailsBox, "From:", ticket.getSeat().getBusInfo().getRoute12().getSourceBusStop().getName(), labelFont, valueFont);
        addTableCell(detailsBox, "Destination:", ticket.getSeat().getBusInfo().getRoute12().getDestinationBusStop().getName(), labelFont, valueFont);
        addTableCell(detailsBox, "Departure Date:", ticket.getSeat().getBusInfo().getDepartureDateTime().toString(), labelFont, valueFont);
        addTableCell(detailsBox, "Price:", "$" + ticket.getSeat().getPrice(), labelFont, valueFont);

        document.add(detailsBox);

        document.close();

        return outputStream.toByteArray();
    }

    private void addTableCell(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell cell = new PdfPCell(new Phrase(label + " " + value, labelFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }
}

