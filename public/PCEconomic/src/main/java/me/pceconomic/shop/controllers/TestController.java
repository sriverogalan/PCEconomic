package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.propietats.Propietat;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.article.propietats.Valor;
import me.pceconomic.shop.domain.entities.persona.Administrador;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.entities.persona.SuperUsuari;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    private final PersonaRepository personaRepository;
    private final ClientRepository clientRepository;
    private final AdministradorRepository administradorRepository;
    private final SuperUsuariRepository superUsuariRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestController(PasswordEncoder passwordEncoder, PersonaRepository personaRepository, ClientRepository clientRepository, AdministradorRepository administradorRepository, SuperUsuariRepository superUsuariRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personaRepository = personaRepository;
        this.clientRepository = clientRepository;
        this.administradorRepository = administradorRepository;
        this.superUsuariRepository = superUsuariRepository;
    }

    @GetMapping("/persones")
    public Iterable<Persona> persones() {
        return personaRepository.findAll();
    }

    @GetMapping("/admins")
    public Iterable<Administrador> admins() {
        return administradorRepository.findAll();
    }

    @GetMapping("/root")
    public Iterable<SuperUsuari> root() {
        return superUsuariRepository.findAll();
    }

    @GetMapping("/test")
    public String encode(@RequestParam String password) {
        return passwordEncoder.encode(password);
    }

}
