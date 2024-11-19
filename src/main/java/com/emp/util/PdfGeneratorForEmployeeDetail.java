package com.emp.util;

import java.io.ByteArrayOutputStream;

import com.emp.model.ResponseEmpDetailDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGeneratorForEmployeeDetail {
    public static byte[] createPdf(ResponseEmpDetailDto responseEmpDetailDto) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = null;

        try {
            // Create a new document
            document = new Document();
            // Initialize PdfWriter
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open(); // Open the document for writing

            // Add a title
            Paragraph title = new Paragraph("Employee Registration Details");
            title.setAlignment(Element.ALIGN_CENTER); // Center the title
            document.add(title);
            document.add(new Paragraph(" ")); // Add space after the title

            // Create a table with 2 columns
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100); // Set table width to 100%
            table.setSpacingBefore(10f); // Space before the table
            table.setSpacingAfter(10f); // Space after the table

            // Add table headers
            PdfPCell headerCell1 = new PdfPCell(new Paragraph("Field"));
            PdfPCell headerCell2 = new PdfPCell(new Paragraph("Details"));
            headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell1);
            table.addCell(headerCell2);

            // Add employee details to the table
            addCellToTable(table, "Employee ID", responseEmpDetailDto.getEmployeeId());
            addCellToTable(table, "Name", responseEmpDetailDto.getFirstName() + " " + responseEmpDetailDto.getLastName());
            addCellToTable(table, "Address", responseEmpDetailDto.getAddress());
            addCellToTable(table, "Country", responseEmpDetailDto.getCountry());
            addCellToTable(table, "Date of Birth", responseEmpDetailDto.getDateOfBirth().toString());
            addCellToTable(table, "Email", responseEmpDetailDto.getEmail());
            addCellToTable(table, "Phone Number", responseEmpDetailDto.getPhoneNumber());
            addCellToTable(table, "Job Title", responseEmpDetailDto.getJobTitle());
            addCellToTable(table, "Date of Joining", responseEmpDetailDto.getDateOfJoining().toString());
            addCellToTable(table, "Employment Type", responseEmpDetailDto.getEmploymentType().toString());
            addCellToTable(table, "Employment Status", responseEmpDetailDto.getEmploymentStatus().toString());

            // Add the table to the document
            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (document != null) {
                document.close(); // Close the document
            }
        }
        return byteArrayOutputStream.toByteArray(); // Return the PDF as a byte array
    }

    // Helper method to add cells to the table with proper spacing
    private static void addCellToTable(PdfPTable table, String header, String value) {
        PdfPCell cell1 = new PdfPCell(new Paragraph(header));
        PdfPCell cell2 = new PdfPCell(new Paragraph(value));

        cell1.setPadding(10); // Set padding for cells
        cell2.setPadding(10);

        // Center align the details column
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);

        table.addCell(cell1);
        table.addCell(cell2);
    }
}
