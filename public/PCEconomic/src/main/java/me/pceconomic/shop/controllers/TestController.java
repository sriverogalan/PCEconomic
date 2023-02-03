package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Direccio;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class TestController {

    private final PersonaRepository personaRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TestController(PersonaRepository personaRepository, ClientRepository clientRepository) {
        this.personaRepository = personaRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/api/persones")
    public List<Persona> test() {
        return personaRepository.findAll();
    }

    @GetMapping("/api/clients")
    public List<Client> test2() {
        return clientRepository.findAll();
    }

}
