package me.pceconomic.shop.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
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


    @PostMapping("/auth/login")
    public ResponseEntity<String> loginGoogle(@RequestBody String token) throws GeneralSecurityException, IOException {
        System.out.println("Token: " + token);
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                HTTP_TRANSPORT,
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        GoogleIdToken.Payload payload = idToken.getPayload();

        System.out.println("Email: " + payload.getEmail());

        /* TODO: A PARTIR DEL MAIL DE GOOGLE VALIDAR SI EXISTEIX A BASE DE DADES Y CREAR TOKEN LOCAL */

        return new ResponseEntity<>("Hola", HttpStatus.OK);
    }

}
