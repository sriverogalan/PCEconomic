package me.pceconomic.shop.controllers;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.pceconomic.shop.domain.entities.article.factura.Factura;
import me.pceconomic.shop.domain.entities.article.factura.LineasFactura;
import me.pceconomic.shop.domain.entities.article.propietats.Propietat;
import me.pceconomic.shop.domain.entities.article.propietats.Valor;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.areaclients.*;
import me.pceconomic.shop.services.AreaClientsService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

@Controller
public class AreaClientsController {

    private final FrontService frontService;
    private final AreaClientsService areaClientsService;

    @Autowired
    public AreaClientsController(FrontService frontService, AreaClientsService areaClientsService) {
        this.frontService = frontService;
        this.areaClientsService = areaClientsService;
    }

    @GetMapping("/areaclients")
    public String areaclients(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");
        model.addAttribute("user", client);
        frontService.sendListsToView(model, request);

        model.addAttribute("changeName", new ChangeNameForm());
        model.addAttribute("directionForm", new AddDirectionForm());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        model.addAttribute("changeEmailForm", new ChangeEmailForm());
        model.addAttribute("changeTelephoneForm", new ChangeTelephoneForm());

        return "areaclients";
    }

    @PostMapping("/areaclients/addDirection/{id}")
    public String addDirection(@PathVariable(value = "id", required = false) int id, HttpServletRequest request, @ModelAttribute AddDirectionForm directionForm, Model model) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");

        if (id == 0) {
            areaClientsService.saveDirection(client, directionForm, session);
            return "redirect:/areaclients";
        }

        try {
            areaClientsService.updateDirection(client, directionForm, id, session);
            return "redirect:/areaclients";
        } catch (Exception e) {
            model.addAttribute("updateDirectionError", "Ya tienes una dirección con ese nombre");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }
    }

    @GetMapping("/areaclients/deleteDirection/{id}")
    public String deleteDirection(HttpServletRequest request, @PathVariable int id, Model model) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");

        try {
            areaClientsService.deleteDirection(client, id, session);
        } catch (IllegalArgumentException e) {
            model.addAttribute("deleteDirection", "Esta dirección no existe o no le pertenece");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }
        return "redirect:/areaclients";
    }

    @PostMapping("/areaclients/addDirection/buying")
    public String addDirectionWhenBuying(HttpServletRequest request, @ModelAttribute AddDirectionForm directionForm) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");

        areaClientsService.saveDirection(client, directionForm, session);
        return "redirect:/carrito/direccion";
    }

    @GetMapping("/areaclients/deletedirection/{id}")
    public String deleteDirection(HttpServletRequest request, @PathVariable int id) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");

        areaClientsService.deleteDirection(client, id, session);
        return "redirect:/areaclients";
    }

    @PostMapping("/areaclients/changeName")
    public String changeName(HttpServletRequest request, @ModelAttribute ChangeNameForm changeName, Model model) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");

        if (!changeName.getNewName().equals(changeName.getConfirmNewName())) {
            model.addAttribute("changeNameError", "Los nombres no coinciden");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        areaClientsService.changeName(client, changeName);
        return "redirect:/areaclients";
    }

    @PostMapping("/areaclients/changepassword")
    public String changePassword(HttpServletRequest request, @ModelAttribute ChangePasswordForm changePasswordForm, Model model) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");

        if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
            model.addAttribute("changePasswordError", "Las contraseñas no coinciden");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        if (!areaClientsService.getPasswordEncoder().matches(changePasswordForm.getOldPassword(), client.getPassword())) {
            model.addAttribute("changePasswordError", "La contraseña no es correcta");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        areaClientsService.changePassword(client, changePasswordForm);
        return "redirect:/areaclients";
    }

    @PostMapping("/areaclients/changeemail")
    public String changeEmail(HttpServletRequest request, @ModelAttribute @Valid ChangeEmailForm changeEmailForm, Model model, BindingResult bindingResult) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");

        if (bindingResult.hasErrors()) {
            model.addAttribute("changeEmailError", "El email no es válido");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        if (!changeEmailForm.getNewEmail().equals(changeEmailForm.getConfirmNewEmail())) {
            model.addAttribute("changeEmailError", "Los emails no coinciden");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        try {
            areaClientsService.changeEmail(client, changeEmailForm);
        } catch (IllegalArgumentException e) {
            model.addAttribute("changeEmailError", "El email ya está en uso");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }
        return "redirect:/areaclients";
    }

    @PostMapping("/areaclients/changetelephone")
    public String changeTelephone(HttpServletRequest request, @ModelAttribute ChangeTelephoneForm changeTelephoneForm, Model model) {
        HttpSession session = request.getSession();
        Persona client = (Persona) session.getAttribute("persona");

        if (!client.getTelefon().equals(changeTelephoneForm.getOldTelephone())) {
            model.addAttribute("changePhoneError", "El teléfono no es correcto");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        if (!changeTelephoneForm.getNewTelephone().equals(changeTelephoneForm.getConfirmNewTelephone())) {
            model.addAttribute("changePhoneError", "Los teléfonos no coinciden");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        try {
            areaClientsService.changeTelephone(client, changeTelephoneForm);
        } catch (IllegalArgumentException e) {
            model.addAttribute("changePhoneError", "El teléfono ya está en uso");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        return "redirect:/areaclients";
    }

    private final Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
    private final Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD);
    private final Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
    NumberFormat formatoEuros = NumberFormat.getCurrencyInstance(Locale.ITALY);

    @GetMapping("/areaclients/generatepdf/{id}")
    public void generatePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
        HttpSession session = request.getSession();
        if (session == null) return;
        Persona client = (Persona) session.getAttribute("persona");

        for (Factura factura : client.getFactures()) {
            if (factura.getId() == id) {
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
        transportParagraph.setSpacingBefore(20);

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

            String vals = "";
            for (Valor v : lf.getPropietats().getValor()) {
                for (Propietat prop : v.getPropietat()) {
                    vals += prop.getNom() + " " + v.getValor() + " ";
                }
            }

            PdfPCell cell = new PdfPCell();
            cell.setColspan(2);
            cell.setPhrase(new Paragraph(lf.getMarca().getName() + " " + lf.getNomArticle() + " " + vals, textFont));

            table.addCell(cell);
            table.addCell(formatoEuros.format(lf.getPrice()));
            table.addCell(formatoEuros.format(lf.getPrice() * lf.getQuantity()));
        }
    }

}
