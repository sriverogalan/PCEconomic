package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.persona.Administrador;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.entities.persona.SuperUsuari;
import me.pceconomic.shop.repositories.AdministradorRepository;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import me.pceconomic.shop.repositories.SuperUsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @GetMapping("/test2")
    public User test2(@RequestParam String email) throws UsernameNotFoundException {
        Persona persona = personaRepository.findByEmail(email);

        if (persona == null) {
            throw new UsernameNotFoundException("No s'ha trobat l'usuari");
        }

        if (clientRepository.findByPersona(persona) != null) {
            return new User(persona.getEmail(), persona.getPassword(), AuthorityUtils.createAuthorityList("CLIENT"));
        } else if (administradorRepository.findByPersona(persona) != null) {
            return new User(persona.getEmail(), persona.getPassword(), AuthorityUtils.createAuthorityList("ADMINISTRADOR"));
        } else if (superUsuariRepository.findByPersona(persona) != null) {
            return new User(persona.getEmail(), persona.getPassword(), AuthorityUtils.createAuthorityList("SUPERUSUARI"));
        } else {
            throw new UsernameNotFoundException("No s'ha trobat l'usuari");
        }
    }
}
