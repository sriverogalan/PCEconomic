package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final PersonaRepository personaRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestController(PasswordEncoder passwordEncoder, PersonaRepository personaRepository, ClientRepository clientRepository) {
        this.personaRepository = personaRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/persones")
    public List<Persona> test() {
        return personaRepository.findAll();
    }

    @GetMapping("/api/clients")
    public List<Client> test2() {
        return clientRepository.findAll();
    }

    @GetMapping("/encode")
    public String encode(@RequestParam String p) {
        return passwordEncoder.encode(p);
    }

}
