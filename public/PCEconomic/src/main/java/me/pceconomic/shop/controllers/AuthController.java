package me.pceconomic.shop.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.services.RegisterService;
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

@RestController
public class AuthController {

    @Value("${google.client_id}")
    private String CLIENT_ID;

    private final RegisterService registerService;

    @Autowired
    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginGoogle(@RequestBody String token, HttpServletRequest request) throws GeneralSecurityException, IOException {
        System.out.println("Token: " + token);
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                HTTP_TRANSPORT,
                GsonFactory.getDefaultInstance()
        ).setAudience(Collections.singletonList(CLIENT_ID))
         .build();

        GoogleIdToken idToken = verifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();

        System.out.println("Email: " + payload.getEmail());

        /* TODO: A PARTIR DEL MAIL DE GOOGLE VALIDAR SI EXISTEIX A BASE DE DADES Y CREAR TOKEN LOCAL */
        Client client = registerService.getClientByPersona(registerService.getPersonaByEmail(payload.getEmail()));
        if (client == null) return new ResponseEntity<>("No existeix", HttpStatus.NOT_FOUND);
        HttpSession session = request.getSession();
        if (session.isNew()) return new ResponseEntity<>("No existeix", HttpStatus.NOT_FOUND);
        session.setAttribute("persona", client);

        return new ResponseEntity<>("Hola", HttpStatus.OK);
    }

}
