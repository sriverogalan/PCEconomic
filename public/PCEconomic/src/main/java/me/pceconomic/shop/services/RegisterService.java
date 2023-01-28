package me.pceconomic.shop.services;

import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.RegisterForm;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RegisterService {

    private final PersonaRepository personaRepository;

    @Autowired
    public RegisterService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona getPersona(String email) {
        return personaRepository.findByEmail(email);
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
        persona.setEmail(registerForm.getEmail());

        if (registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            String password = passwordEncoder().encode(registerForm.getPassword());
            persona.setPassword(password);
        } else {
            throw new RuntimeException("Passwords don't match");
        }

        personaRepository.save(persona);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
