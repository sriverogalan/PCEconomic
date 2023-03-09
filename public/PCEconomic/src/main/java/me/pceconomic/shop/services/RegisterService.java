package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.entities.persona.Rols;
import me.pceconomic.shop.domain.forms.login.RegisterForm;
import me.pceconomic.shop.repositories.PersonaRepository;
import me.pceconomic.shop.repositories.RolsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolsRepository rolsRepository;

    @Autowired
    public RegisterService(PasswordEncoder passwordEncoder, RolsRepository rolsRepository, PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolsRepository = rolsRepository;
    }

    public Persona getPersonaByEmail(String email) {
        return personaRepository.findByEmail(email);
    }
    public Rols getRolsByName(String name) {
        return rolsRepository.getRolsByName(name);
    }

    public void deletePersonaByEmail(String email) {
        personaRepository.deletePersonaByEmail(email);
    }

    public void updatePersona(Persona persona) {
        personaRepository.save(persona);
    }

    public void savePersona(Persona persona, RegisterForm registerForm) {
        persona.setName(registerForm.getName());
        persona.setSurname1(registerForm.getSurname1());
        persona.setSurname2(registerForm.getSurname2());


        if (registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            String password = passwordEncoder.encode(registerForm.getPassword());
            persona.setPassword(password);
        } else {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        if (personaRepository.existsByEmail(registerForm.getEmail())) {
            throw new IllegalArgumentException("El correo introducido ya existe");
        }

        if (personaRepository.existsByDni(registerForm.getDni())) {
            throw new IllegalArgumentException("El DNI introducido ya existe");
        }

        if (personaRepository.existsByTelefon(registerForm.getTelefon())) {
            throw new IllegalArgumentException("El teléfono introducido ya existe");
        }

        persona.setEmail(registerForm.getEmail());
        persona.setTelefon(registerForm.getTelefon());
        persona.setDni(registerForm.getDni());
        persona.setActive(false);

        personaRepository.save(persona);
    }

    public void setSession(HttpSession session, Persona persona) {
        session.setAttribute("persona", persona);
        session.setAttribute("direccions", persona.getDireccions());
        session.setAttribute("pedidos", persona.getFactures());
    }

    public void removeSession(HttpSession session) {
        session.removeAttribute("persona");
        session.removeAttribute("direccions");
        session.removeAttribute("pedidos");
        session.removeAttribute("token");
    }
}
