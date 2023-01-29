package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findByPersona(Persona persona);
    Client findByPersonaId(int id);


}
