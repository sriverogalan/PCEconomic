package me.pceconomic.shop.services;

import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RegisterService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private boolean patternMatches(String email) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
                .matcher(email)
                .matches();
    }

    public void savePersona(Persona persona, RegisterForm registerForm) {
        persona.setName(registerForm.getName());
        persona.setSurname1(registerForm.getSurname1());
        persona.setSurname2(registerForm.getSurname2());

        String email = registerForm.getEmail();
        persona.setEmail(email);

        /*if (!patternMatches(email)) {
            throw new IllegalArgumentException("Email is not valid");
        } else {
            persona.setEmail(email);
        }*/

        String password = passwordEncoder.encode(registerForm.getPassword());
        persona.setPassword(password);
    }
}
