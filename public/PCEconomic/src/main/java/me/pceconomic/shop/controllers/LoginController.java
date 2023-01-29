package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.LoginForm;
import me.pceconomic.shop.domain.forms.RegisterForm;
import me.pceconomic.shop.services.FrontService;
import me.pceconomic.shop.services.MailService;
import me.pceconomic.shop.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final RegisterService registerService;
    private final FrontService frontService;
    private final MailService mailService;

    @Autowired
    public LoginController(FrontService frontService,MailService mailService, RegisterService registerService) {
        this.registerService = registerService;
        this.frontService = frontService;
        this.mailService = mailService;
    }

    @GetMapping("/login")
    public String preLogin(Model model) {

        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(HttpServletRequest request, @ModelAttribute("loginForm") LoginForm loginForm) {
        Persona persona = registerService.getPersonaByEmail(loginForm.getEmail());
        Client client = registerService.getClientByPersona(persona);

        if (persona == null || client == null) return "redirect:/login";
        if (!client.isActive()) return "redirect:/login";

        if (registerService.passwordEncoder().matches(loginForm.getPassword(), persona.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("persona", client);
            return "redirect:/areaclients";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String preRegister(Model model) {

        RegisterForm registerForm = new RegisterForm();
        model.addAttribute("registerForm", registerForm);

        frontService.sendListsToView(model);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("registerForm") RegisterForm registerForm) {
        Persona persona = new Persona();
        registerService.savePersona(persona, registerForm);
        mailService.sendMail(registerForm.getEmail(), "Welcome to PC Economic", "Use the link below to confirm your registration: http://localhost:8080/confirmregister/123456789/" + persona.getId());
        return "confirmregister";
    }

    @GetMapping("/confirmregister/{token}/{clientid}")
    public String confirmRegister(Model model, @PathVariable String token, @PathVariable int clientid) {

        if (!token.equals("123456789")) return "redirect:/";

        Client client = registerService.getClientById(clientid);
        client.setActive(true);
        registerService.updateClient(client);

        return "redirect:/";
    }

    @GetMapping("/areaclients")
    public String areaclients(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("user", session.getAttribute("persona"));
        frontService.sendListsToView(model);
        return "areaclients";
    }
}
