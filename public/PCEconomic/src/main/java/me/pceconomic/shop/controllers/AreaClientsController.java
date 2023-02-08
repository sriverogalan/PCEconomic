package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.pceconomic.shop.domain.entities.persona.Client;
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
        model.addAttribute("user", session.getAttribute("persona"));
        frontService.sendListsToView(model, request);

        model.addAttribute("changeName", new ChangeNameForm());
        model.addAttribute("directionForm", new AddDirectionForm());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        model.addAttribute("changeEmailForm", new ChangeEmailForm());
        model.addAttribute("changeTelephoneForm", new ChangeTelephoneForm());

        return "areaclients";
    }

    @PostMapping("/areaclients/addDirection")
    public String addDirection(HttpServletRequest request, @ModelAttribute AddDirectionForm directionForm, Model model) {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("persona");

        if (directionForm.getId() == 0) {
            try {
                areaClientsService.saveDirection(client, directionForm);
                return "redirect:/areaclients";
            } catch (Exception e) {
                model.addAttribute("updateDirectionError", "Ya tienes una dirección con ese nombre");
                areaClientsService.sendToModel(model, session);
                return "areaclients";
            }
        }

        areaClientsService.saveDirection(client, directionForm);
        return "redirect:/areaclients";
    }

    @GetMapping("/areaclients/deleteDirection/{id}")
    public String deleteDirection(HttpServletRequest request, @PathVariable int id, Model model) {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("persona");

        try {
            areaClientsService.deleteDirection(client, id);
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
        Client client = (Client) session.getAttribute("persona");

        areaClientsService.saveDirection(client, directionForm);
        return "redirect:/carrito/direccion";
    }

    @GetMapping("/areaclients/deletedirection/{id}")
    public String deleteDirection(HttpServletRequest request, @PathVariable int id) {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("persona");

        areaClientsService.deleteDirection(client, id);
        return "redirect:/areaclients";
    }

    @PostMapping("/areaclients/changeName")
    public String changeName(HttpServletRequest request, @ModelAttribute ChangeNameForm changeName, Model model) {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("persona");

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
        Client client = (Client) session.getAttribute("persona");

        if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
            model.addAttribute("changePasswordError", "Las contraseñas no coinciden");
            areaClientsService.sendToModel(model, session);
            return "areaclients";
        }

        if (!areaClientsService.getPasswordEncoder().matches(changePasswordForm.getOldPassword(), client.getPersona().getPassword())) {
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
        Client client = (Client) session.getAttribute("persona");

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
        Client client = (Client) session.getAttribute("persona");

        if (!client.getPersona().getTelefon().equals(changeTelephoneForm.getOldTelephone())) {
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

}
