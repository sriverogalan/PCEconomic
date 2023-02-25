package me.pceconomic.shop.services;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import me.pceconomic.shop.domain.entities.article.factura.Factura;
import me.pceconomic.shop.domain.entities.article.factura.LineasFactura;
import me.pceconomic.shop.domain.entities.article.propietats.Propietat;
import me.pceconomic.shop.domain.entities.article.propietats.Valor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

@Service
public class PDFService {

    private final Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
    private final Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD);
    private final Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
    NumberFormat formatoEuros = NumberFormat.getCurrencyInstance(Locale.ITALY);

    public void generatePdf(HttpServletResponse response, Factura factura) {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=invoice.pdf";
        response.setHeader(headerKey, headerValue);

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            this.generateDocument(document, factura);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateDocument(Document document, Factura factura) {
        Paragraph title = new Paragraph("Factura nº " + factura.getId(), titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Información de la factura
        document.add(new Paragraph("ID de factura: " + factura.getId(), headerFont));
        document.add(new Paragraph("Fecha: " + factura.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), textFont));
        document.add(new Paragraph("Cliente: " + factura.getClient().getName() + " " + factura.getClient().getSurname1(), textFont));
        document.add(new Paragraph("Dirección de envío: " + factura.getDireccio(), textFont));
        document.add(new Paragraph("Método de pago: " + factura.getMetodePagament(), textFont));
        document.add(new Paragraph("Estado: " + factura.getEstat(), textFont));

        // Tabla con las líneas de factura
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(20);
        table.setSpacingAfter(20);
        this.addTableHeader(table);
        this.addRows(table, factura.getLineasFacturas());
        document.add(table);

        // Precio total
        double total = factura.getPreu() + factura.getPreuTransport();
        Paragraph transportParagraph = new Paragraph("Precio del transporte: " + formatoEuros.format(factura.getPreuTransport()), textFont);
        Paragraph totalParagraph = new Paragraph("Precio total: " + formatoEuros.format(total), textFont);

        transportParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        totalParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
        totalParagraph.setSpacingBefore(20);

        document.add(transportParagraph);
        document.add(totalParagraph);
    }

    private void addTableHeader(PdfPTable table) {
        String[] headers = {"Cantidad", "Artículo", "Precio", "Subtotal"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(Color.LIGHT_GRAY);
            headerCell.setPhrase(new Paragraph(header, headerFont));
            if (header.equals("Artículo")) headerCell.setColspan(2);
            table.addCell(headerCell);
        }
    }

    private void addRows(PdfPTable table, Set<LineasFactura> lineasFacturas) {
        for (LineasFactura lf : lineasFacturas) {
            table.addCell("x" + Integer.toString(lf.getQuantity()));

            PdfPCell cell = new PdfPCell();
            cell.setColspan(2);
            cell.setPhrase(new Paragraph(lf.getMarca().getName() + " " + lf.getNomArticle() + " " + lf.getPropietats(), textFont));

            table.addCell(cell);
            table.addCell(formatoEuros.format(lf.getPrice() / lf.getQuantity()));
            table.addCell(formatoEuros.format(lf.getPrice()));
        }
    }

}
