package com.Transaction.transaction.service;


import com.Transaction.transaction.entity.Ticket;
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

        // Ticket details table
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.setSpacingAfter(10f);

        // Ticket details
        Font labelFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
        Font valueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);

        addTableCell(detailsTable, "Ticket Number:", String.valueOf(ticket.getTicketNo()), labelFont, valueFont);
        addTableCell(detailsTable, "Passenger FullName:", ticket.getBookingTicket().getFullName(), labelFont, valueFont);
        addTableCell(detailsTable, "Seat Number:", ticket.getSeatNumber(), labelFont, valueFont);
        addTableCell(detailsTable, "From:", ticket.getSeat().getBusInfo().getRoute12().getSourceBusStop().getName(), labelFont, valueFont);
        addTableCell(detailsTable, "Destination:", ticket.getSeat().getBusInfo().getRoute12().getDestinationBusStop().getName(), labelFont, valueFont);
        addTableCell(detailsTable, "Departure Date:", ticket.getSeat().getBusInfo().getDepartureDateTime().toString(), labelFont, valueFont);
        addTableCell(detailsTable, "Price:", "Nrs" + ticket.getSeat().getPrice(), labelFont, valueFont);

        document.add(detailsTable);

        document.close();

        return outputStream.toByteArray();
    }

    private void addTableCell(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));

        labelCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setBorder(Rectangle.NO_BORDER);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}

