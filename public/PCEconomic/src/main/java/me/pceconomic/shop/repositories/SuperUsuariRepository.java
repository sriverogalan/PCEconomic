package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.entities.persona.SuperUsuari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperUsuariRepository extends JpaRepository<SuperUsuari, Integer> {
    SuperUsuari findByPersona(Persona persona);
}
