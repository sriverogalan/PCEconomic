package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Direccio;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.LoginForm;
import me.pceconomic.shop.domain.forms.RegisterForm;
import me.pceconomic.shop.services.FrontService;
import me.pceconomic.shop.services.MailService;
import me.pceconomic.shop.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
public class LoginController {

    private final RegisterService registerService;
    private final FrontService frontService;
    private final MailService mailService;

    @Autowired
    public LoginController(FrontService frontService, MailService mailService, RegisterService registerService) {
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
    public String postLogin(HttpServletRequest request, @ModelAttribute("loginForm") @Valid LoginForm loginForm, Model model) {
        Persona persona = registerService.getPersonaByEmail(loginForm.getEmail());
        Client client = registerService.getClientByPersona(persona);

        if (persona == null || client == null) {
            model.addAttribute("error", "Tu correo electronico o tu contraseña no son validos");
            return "login";
        }
        if (!client.isActive()) {
            model.addAttribute("error", "Tienes que activar tu cuenta antes de iniciar sesión");
            return "redirect:/login";
        }

        if (!registerService.passwordEncoder().matches(loginForm.getPassword(), persona.getPassword())) {
            model.addAttribute("error", "Tu correo electronico o tu contraseña no son validos");
            return "login";
        }

        if (registerService.passwordEncoder().matches(loginForm.getPassword(), persona.getPassword())) {
            HttpSession session = request.getSession();
            Set<Direccio> direccions = client.getPersona().getDireccions();

            session.setAttribute("persona", client);
            session.setAttribute("direccions", direccions);
            return "redirect:/";
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
    public String preRegister(Model model, HttpServletRequest request) {

        RegisterForm registerForm = new RegisterForm();
        model.addAttribute("registerForm", registerForm);

        frontService.sendListsToView(model, request);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("registerForm") @Valid RegisterForm registerForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
                model.addAttribute("passwordmatch", "Las contraseñas introducidas no coinciden");
                return "register";
            }
            return "register";
        }

        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            model.addAttribute("passwordmatch", "Las contraseñas introducidas no coinciden");
            return "register";
        }

        Persona persona = new Persona();
        try {
            registerService.savePersona(persona, registerForm);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "El correo electronico introducido ya esta registrado");
            return "register";
        }

        mailService.sendMail(registerForm.getEmail(), "Welcome to PC Economic", "Use the link below to confirm your registration: http://localhost:8080/confirmregister/123456789/" + persona.getId());
        return "confirmregister";
    }

    @GetMapping("/confirmregister/{token}/{clientid}")
    public String confirmRegister(@PathVariable String token, @PathVariable int clientid) {

        if (!token.equals("123456789")) return "redirect:/error";

        Client client = registerService.getClientById(clientid);
        client.setActive(true);
        registerService.updateClient(client);

        return "redirect:/";
    }
}
