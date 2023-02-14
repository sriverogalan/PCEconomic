package me.pceconomic.shop.controllers;

import io.jsonwebtoken.Claims;
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
import me.pceconomic.shop.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Set;

@Controller
public class LoginController {

    private final RegisterService registerService;
    private final FrontService frontService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public LoginController(FrontService frontService,TokenService tokenService, PasswordEncoder passwordEncoder, MailService mailService, RegisterService registerService) {
        this.registerService = registerService;
        this.frontService = frontService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
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

        if (!passwordEncoder.matches(loginForm.getPassword(), persona.getPassword())) {
            model.addAttribute("error", "Tu correo electronico o tu contraseña no son validos");
            return "login";
        }

        if (passwordEncoder.matches(loginForm.getPassword(), persona.getPassword())) {
            HttpSession session = request.getSession();
            Set<Direccio> direccions = client.getPersona().getDireccions();

            session.setAttribute("persona", client);
            session.setAttribute("direccions", direccions);
            session.setAttribute("pedidos", client.getFactures());
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
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            model.addAttribute("passwordmatch", "Las contraseñas introducidas no coinciden");
            return "register";
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        Persona persona = new Persona();
        try {
            registerService.savePersona(persona, registerForm);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "El correo electronico introducido ya esta registrado");
            return "register";
        }

        String token = tokenService.createToken(persona.getEmail(), new ArrayList<>());
        mailService.sendMail(registerForm.getEmail(), "Welcome to PC Economic", "Use the link below to confirm your registration: http://pceconomic.live:8080/confirmregister/" + token);
        return "confirmregister";
    }

    @GetMapping("/confirmregister/{token}")
    public String confirmRegister(@PathVariable String token) {
        Claims claims = tokenService.getClaims(token);
        String email = claims.get("email", String.class);

        int valid = tokenService.validateToken(token);

        if (valid == 0) return "redirect:/";
        if (valid == 2) return "redirect:/";

        if (valid == 1) {
            Client client = registerService.getClientByPersona(registerService.getPersonaByEmail(email));
            client.setActive(true);
            registerService.updateClient(client);
        }

        return "redirect:/";
    }
}
