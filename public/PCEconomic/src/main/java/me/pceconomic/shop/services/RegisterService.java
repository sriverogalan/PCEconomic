package me.pceconomic.shop.services;

import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.RegisterForm;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final PersonaRepository personaRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Persona getPersonaByEmail(String email) {
        return personaRepository.findByEmail(email);
    }

    public Client getClientById(int id) {
        return clientRepository.findByPersonaId(id);
    }

    public Client getClientByPersona(Persona persona) {
        return clientRepository.findByPersona(persona);
    }

    public void updateClient(Client client) {
        clientRepository.save(client);
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

        personaRepository.save(persona);

        Client client = new Client();
        client.setPersona(persona);
        client.setDni(registerForm.getDni());
        client.setActive(false);
        client.setSubscribed(false);

        clientRepository.save(client);
    }
}
