package me.pceconomic.shop.controllers;

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

import java.util.HashSet;
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
    public String preLogin(Model model) {

        LoginForm loginForm = new LoginForm();
        model.addAttribute("loginForm", loginForm);

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

        if (passwordEncoder.matches(loginForm.getPassword(), persona.getPassword())) {
            HttpSession session = request.getSession();
            registerService.setSession(session, persona);
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

        String token = tokenService.createToken(persona.getEmail(), new HashSet<>(), TimeUnit.HOURS.toMillis(1));
        mailService.sendMail(registerForm.getEmail(), "Welcome to PC Economic", "Use the link below to confirm your registration: http://www.pceconomic.me/confirmregister/" + token + " \n\n" +
                "You have 1 hour to confirm your registration. After that, you will have to register again.");
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
        registerService.updatePersona(persona);
        persona.getRols().add(rol);


        return "redirect:/";
    }

    @GetMapping("/forgotpassword")
    public String preForgotPassword(Model model) {
        SendEmailForm sendEmailForm = new SendEmailForm();
        model.addAttribute("sendEmailForm", sendEmailForm);
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
        mailService.sendMail(sendEmailForm.getEmail(), "PC Economic - Password recovery", "Use the link below to recover your password: http://pceconomic.live:8080/changepassword/" + token + " \n\n" +
                "You have 1 hour to recover your password. After that, you will have to request a.html new password recovery.");
        return "redirect:/";
    }

    @GetMapping("/changepassword/{token}")
    public String preChangePassword(@PathVariable String token, Model model) {
        int valid = tokenService.validateToken(token);

        if (valid == 1) return "redirect:/";
        if (valid == 2) return "redirect:/";

        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        model.addAttribute("changePasswordForm", changePasswordForm);
        model.addAttribute("token", token);
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
