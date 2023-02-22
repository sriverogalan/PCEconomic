package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.pceconomic.shop.domain.entities.article.factura.Factura;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.AddValorationForm;
import me.pceconomic.shop.domain.forms.areaclients.*;
import me.pceconomic.shop.services.AreaClientsService;
import me.pceconomic.shop.services.FrontService;
import me.pceconomic.shop.services.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class AreaClientsController {

    private final FrontService frontService;
    private final AreaClientsService areaClientsService;
    private final PDFService pdfService;

    @Autowired
    public AreaClientsController(FrontService frontService, PDFService pdfService, AreaClientsService areaClientsService) {
        this.frontService = frontService;
        this.areaClientsService = areaClientsService;
        this.pdfService = pdfService;
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

    @GetMapping("/areaclients/generatepdf/{id}")
    public void generatePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
        HttpSession session = request.getSession();
        if (session == null) return;
        Persona client = (Persona) session.getAttribute("persona");

        for (Factura factura : client.getFactures()) {
            if (factura.getId() == id) {
                pdfService.generatePdf(response, factura);
            }
        }
    }

}
