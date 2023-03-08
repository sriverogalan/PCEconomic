package me.pceconomic.shop.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.entities.persona.Rols;
import me.pceconomic.shop.services.RegisterService;
import me.pceconomic.shop.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
public class AuthController {

    @Value("${google.client_id}")
    private String CLIENT_ID;

    private final RegisterService registerService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(RegisterService registerService, TokenService tokenService) {
        this.registerService = registerService;
        this.tokenService = tokenService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginGoogle(@RequestBody String token, HttpServletRequest request) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();

        Persona client = registerService.getPersonaByEmail(payload.getEmail());
        if (client == null) return new ResponseEntity<>("No existeix", HttpStatus.NOT_FOUND);

        HttpSession session = request.getSession();
        if (session.isNew()) return new ResponseEntity<>("No existeix", HttpStatus.NOT_FOUND);

        registerService.setSession(session, client);

        Set<String> rols = new HashSet<>(client.getRols().stream().map(Rols::getName).toList());
        String jwtToken = tokenService.createToken(payload.getEmail(), rols, TimeUnit.DAYS.toMillis(7));
        session.setAttribute("token", jwtToken);

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

}
