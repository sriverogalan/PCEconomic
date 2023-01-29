package me.pceconomic.shop.services;

import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.RegisterForm;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final PersonaRepository personaRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public RegisterService(ClientRepository clientRepository, PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
        this.clientRepository = clientRepository;
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

        if (registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            String password = passwordEncoder().encode(registerForm.getPassword());
            persona.setPassword(password);
        } else {
            throw new RuntimeException("Passwords don't match");
        }

        personaRepository.save(persona);

        Client client = new Client();
        client.setPersona(persona);
        client.setActive(false);
        client.setSubscribed(false);

        clientRepository.save(client);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
