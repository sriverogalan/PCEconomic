package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.RegisterForm;
import me.pceconomic.shop.repositories.PersonaRepository;
import me.pceconomic.shop.services.FrontService;
import me.pceconomic.shop.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final RegisterService registerService;
    private final FrontService frontService;
    private final PersonaRepository personaRepository;

    @Autowired
    public LoginController(FrontService frontService, RegisterService registerService, PersonaRepository personaRepository) {
        this.registerService = registerService;
        this.personaRepository = personaRepository;
        this.frontService = frontService;
    }

/*    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }*/

    @GetMapping("/register")
    public String register(Model model) {

        RegisterForm registerForm = new RegisterForm();
        model.addAttribute("registerForm", registerForm);

        frontService.sendListsToView(model);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("registerForm") RegisterForm registerForm) {
        Persona persona = new Persona();
        registerService.savePersona(persona, registerForm);

        personaRepository.save(persona);
        return "redirect:/";
    }

    @GetMapping("/areaclients")
    public String areaclients(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("user", session.getAttribute("user"));
        frontService.sendListsToView(model);
        return "areaclients";
    }
}
