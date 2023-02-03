package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.forms.AddDirectionForm;
import me.pceconomic.shop.domain.forms.ChangeNameForm;
import me.pceconomic.shop.services.AreaClientsService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        return "areaclients";
    }

    @PostMapping("/areaclients/addDirection")
    public String addDirection(HttpServletRequest request, @ModelAttribute AddDirectionForm directionForm) {
        HttpSession session = request.getSession();
        if (session == null) return "redirect:/";

        Client client = (Client) session.getAttribute("persona");

        if (client == null) return "redirect:/login";

        areaClientsService.saveDirection(client, directionForm);
        return "redirect:/areaclients";
    }

    @PostMapping("/areaclients/changeName")
    public String changeName(HttpServletRequest request, @ModelAttribute ChangeNameForm changeNameForm, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "redirect:/";

        Client client = (Client) session.getAttribute("persona");

        if (client == null) return "redirect:/login";

        if (!changeNameForm.getNewName().equals(changeNameForm.getConfirmNewName())) {
            model.addAttribute("error", "Los nombres no coinciden");
            return "areaclients";
        }

        areaClientsService.changeName(client, changeNameForm);
        return "redirect:/areaclients";
    }

}
