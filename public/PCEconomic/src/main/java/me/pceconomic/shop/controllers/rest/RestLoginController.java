package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.entities.persona.Rols;
import me.pceconomic.shop.services.RegisterService;
import me.pceconomic.shop.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity<String> validacioLogin(
            @ModelAttribute("email") String email,
            @ModelAttribute("password") String password,
            HttpSession session) {
        if (registerService.getPersonaByEmail(email) != null) {
            Persona usuari = registerService.getPersonaByEmail(email);

            if (!usuari.isActive()) {
                return new ResponseEntity<>("Tienes que activar tu cuenta antes de iniciar sesión", HttpStatus.UNAUTHORIZED);
            }

            if (passwordEncoder.matches(password, usuari.getPassword())) {
                Persona client = registerService.getPersonaByEmail(email);
                registerService.setSession(session, client);
                Set<String> rols = new HashSet<>();
                if (usuari.getRols() != null) {
                    rols.addAll(usuari.getRols().stream().map(Rols::getName)
                            .toList());
                }
                String token = tokenService.createToken(email, rols, TimeUnit.DAYS.toMillis(7));
                session.setAttribute("token", token);
                return new ResponseEntity<>(
                        token,
                        HttpStatus.OK);
            }
            return new ResponseEntity<>("Tu correo electronico o tu contraseña no son validos", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Tu correo electronico o tu contraseña no son validos", HttpStatus.UNAUTHORIZED);
    }
}