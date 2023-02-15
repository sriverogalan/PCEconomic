package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.services.RegisterService;
import me.pceconomic.shop.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RestLoginController {

    private final TokenService tokenService;
    private final RegisterService registerService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RestLoginController(TokenService tokenService, PasswordEncoder passwordEncoder, RegisterService registerService) {
        this.tokenService = tokenService;
        this.registerService = registerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/restlogin")
    public ResponseEntity<?> validacioLogin(
            @ModelAttribute("email") String email,
            @ModelAttribute("password") String password,
            HttpSession session) {
        if (registerService.getPersonaByEmail(email) != null) {
            Persona usuari = registerService.getPersonaByEmail(email);
            if (passwordEncoder.matches(password, usuari.getPassword())) {
                Set<String> rols = new HashSet<>();
                return new ResponseEntity<>(
                        tokenService.createToken(email, new ArrayList<>()), HttpStatus.OK);
            }
            String notificacio = "Usuari no autoritzat!";
            return new ResponseEntity<>(notificacio, HttpStatus.UNAUTHORIZED);
        }
        String notificacio = "Usuari no autoritzat!";
        return new ResponseEntity<>(notificacio, HttpStatus.UNAUTHORIZED);
    }
}