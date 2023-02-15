package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.login.RegisterForm;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(PasswordEncoder passwordEncoder, PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Persona getPersonaByEmail(String email) {
        return personaRepository.findByEmail(email);
    }

    public void updatePersona(Persona persona) {
        personaRepository.save(persona);
    }

    public void savePersona(Persona persona, RegisterForm registerForm) {
        persona.setName(registerForm.getName());
        persona.setSurname1(registerForm.getSurname1());
        persona.setSurname2(registerForm.getSurname2());
        persona.setEmail(registerForm.getEmail());
        persona.setTelefon(registerForm.getTelefon());

        if (registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            String password = passwordEncoder.encode(registerForm.getPassword());
            persona.setPassword(password);
        } else {
            throw new RuntimeException("Passwords don't match");
        }

        if (personaRepository.existsByEmail(persona.getEmail())) {
            throw new IllegalArgumentException("Persona already exists");
        }

        persona.setDni(registerForm.getDni());
        persona.setActive(false);
        persona.setSubscribed(false);

        personaRepository.save(persona);
    }

    public void setSession(HttpSession session, Persona persona) {
        session.setAttribute("persona", persona);
        session.setAttribute("direccions", persona.getDireccions());
        session.setAttribute("pedidos", persona.getFactures());
    }
}
