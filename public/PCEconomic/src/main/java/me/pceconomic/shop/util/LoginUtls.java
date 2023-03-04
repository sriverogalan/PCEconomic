package me.pceconomic.shop.util;

import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoginUtls {
    
    private final PersonaRepository personaRepository;

    @Autowired
    public LoginUtls(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void removeInvalidUsers() {
        for (Persona persona : personaRepository.findAll()) {
            if (!persona.isActive()) {
                personaRepository.deletePersonaByEmail(persona.getEmail());
            }
        }
    }
}
