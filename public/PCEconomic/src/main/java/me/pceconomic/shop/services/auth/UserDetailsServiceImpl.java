package me.pceconomic.shop.services.auth;

import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.repositories.AdministradorRepository;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import me.pceconomic.shop.repositories.SuperUsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonaRepository personaRepository;
    private final ClientRepository clientRepository;
    private final AdministradorRepository administradorRepository;
    private final SuperUsuariRepository superUsuariRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, PersonaRepository personaRepository, ClientRepository clientRepository, AdministradorRepository administradorRepository, SuperUsuariRepository superUsuariRepository) {
        this.personaRepository = personaRepository;
        this.clientRepository = clientRepository;
        this.administradorRepository = administradorRepository;
        this.superUsuariRepository = superUsuariRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Persona persona = personaRepository.findByEmail(email);

        if (persona == null) {
            throw new UsernameNotFoundException("No s'ha trobat l'usuari");
        }

        persona.setPassword(passwordEncoder.encode(persona.getPassword()));

        if (clientRepository.findByPersona(persona) != null) {
            return new User(persona.getEmail(), persona.getPassword(), AuthorityUtils.createAuthorityList("CLIENT"));
        } else if (administradorRepository.findByPersona(persona) != null) {
            return new User(persona.getEmail(), persona.getPassword(), AuthorityUtils.createAuthorityList("ADMINISTRADOR"));
        } else if (superUsuariRepository.findByPersona(persona) != null) {
            return new User(persona.getEmail(), persona.getPassword(), AuthorityUtils.createAuthorityList("SUPERUSUARI"));
        }

        return new User(persona.getEmail(), persona.getPassword(), AuthorityUtils.createAuthorityList("PERSONA"));
    }
}
