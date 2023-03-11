package me.pceconomic.shop.controllers;

import com.google.api.Http;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.entities.persona.Rols;
import me.pceconomic.shop.domain.forms.areaclients.ChangePasswordForm;
import me.pceconomic.shop.domain.forms.login.LoginForm;
import me.pceconomic.shop.domain.forms.login.RegisterForm;
import me.pceconomic.shop.domain.forms.login.SendEmailForm;
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
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {

    private final RegisterService registerService;
    private final FrontService frontService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    public LoginController(FrontService frontService, TokenService tokenService, PasswordEncoder passwordEncoder, MailService mailService, RegisterService registerService) {
        this.registerService = registerService;
        this.frontService = frontService;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @GetMapping("/login")
    public String preLogin(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) registerService.removeSession(session);

        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);
        frontService.sendListsToView(model, request);

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(HttpServletRequest request, @ModelAttribute("loginForm") @Valid LoginForm loginForm, Model model) {
        Persona persona = registerService.getPersonaByEmail(loginForm.getEmail());

        if (persona == null) {
            model.addAttribute("error", "Tu correo electronico o tu contraseña no son validos");
            return "login";
        }
        if (!persona.isActive()) {
            model.addAttribute("error", "Tienes que activar tu cuenta antes de iniciar sesión");
            return "redirect:/login";
        }

        if (!passwordEncoder.matches(loginForm.getPassword(), persona.getPassword())) {
            model.addAttribute("error", "Tu correo electronico o tu contraseña no son validos");
            return "login";
        }

        HttpSession session = request.getSession();
        registerService.setSession(session, persona);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) registerService.removeSession(session);
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
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        String token = tokenService.createToken(persona.getEmail(), new HashSet<>(), TimeUnit.HOURS.toMillis(24));

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Hola ").append(persona.getName()).append(" ").append(persona.getSurname1()).append("\n\n");
        emailBody.append("Gracias por registrarte en nuestra plataforma. Estamos encantados de que formes parte de nuestra comunidad.\n\n");
        emailBody.append("Para completar tu registro y acceder a todos nuestros servicios, solo tienes que hacer clic en el botón de abajo:\n\n");
        emailBody.append("http://www.pceconomic.me/confirmregister/").append(token).append("\n\n");
        emailBody.append("Un saludo,\nEl equipo de PCEconomic");

        mailService.sendMail(registerForm.getEmail(), "Bienvenido a PCEconomic", emailBody.toString());
        return "confirmregister";
    }

    @GetMapping("/confirmregister/{token}")
    public String confirmRegister(@PathVariable String token) {
        int valid = tokenService.validateToken(token);

        if (valid == 1) return "redirect:/";
        if (valid == 2) return "redirect:/";

        Claims claims = tokenService.getClaims(token);
        String email = claims.get("email", String.class);

        Persona persona = registerService.getPersonaByEmail(email);
        Rols rol = registerService.getRolsByName("CLIENT");

        persona.setActive(true);
        persona.getRols().add(rol);
        registerService.updatePersona(persona);

        return "redirect:/";
    }

    @GetMapping("/forgotpassword")
    public String preForgotPassword(Model model, HttpServletRequest request) {
        SendEmailForm sendEmailForm = new SendEmailForm();
        model.addAttribute("sendEmailForm", sendEmailForm);
        frontService.sendListsToView(model, request);
        return "lostpassword";
    }

    @PostMapping("/forgotpassword")
    public String postForgotPassword(@ModelAttribute("sendEmailForm") @Valid SendEmailForm sendEmailForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "lostpassword";
        }

        Persona persona = registerService.getPersonaByEmail(sendEmailForm.getEmail());
        if (persona == null) {
            model.addAttribute("error", "El correo electronico introducido no esta registrado");
            return "lostpassword";
        }

        String token = tokenService.createToken(persona.getEmail(), new HashSet<>(), TimeUnit.MINUTES.toMillis(10));

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Hola ").append(persona.getName()).append(" ").append(persona.getSurname1()).append(",\n\n");
        emailBody.append("Hemos recibido una solicitud para recuperar tu cuenta en nuestra plataforma. Si has sido tú quien ha hecho esta solicitud, por favor haz clic en el siguiente enlace para restablecer tu contraseña:\n\n");
        emailBody.append("http://pceconomic.live:8080/changepassword/").append(token).append("\n\n");
        emailBody.append("Si no has sido tú quien ha hecho esta solicitud, ignora este mensaje y contacta con nuestro servicio de atención al cliente lo antes posible.\n\n");
        emailBody.append("Lamentamos las molestias que esto pueda causarte.\n\n");
        emailBody.append("Un saludo,\nEl equipo de PCEconomic\n");

        mailService.sendMail(sendEmailForm.getEmail(), "Recuperar contraseña", emailBody.toString());
        return "redirect:/";
    }

    @GetMapping("/changepassword/{token}")
    public String preChangePassword(@PathVariable String token, Model model, HttpServletRequest request) {
        int valid = tokenService.validateToken(token);

        if (valid == 1) return "redirect:/";
        if (valid == 2) return "redirect:/";

        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        model.addAttribute("changePasswordForm", changePasswordForm);
        model.addAttribute("token", token);
        frontService.sendListsToView(model, request);
        return "changepassword";
    }

    @PostMapping("/changepassword/{token}")
    public String postChangePassword(@PathVariable String token, @ModelAttribute("changePasswordForm") @Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult, Model model) {
        int valid = tokenService.validateToken(token);

        if (valid == 1) return "redirect:/";
        if (valid == 2) return "redirect:/";

        if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
            model.addAttribute("passwordmatch", "Las contraseñas introducidas no coinciden");
            return "changepassword/" + token;
        }

        if (bindingResult.hasErrors()) {
            return "changepassword/" + token;
        }

        Claims claims = tokenService.getClaims(token);
        String email = claims.get("email", String.class);

        Persona persona = registerService.getPersonaByEmail(email);
        persona.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
        registerService.updatePersona(persona);

        return "redirect:/login";
    }
}
