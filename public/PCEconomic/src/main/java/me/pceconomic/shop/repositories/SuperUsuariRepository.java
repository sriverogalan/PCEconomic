package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.persona.Persona;
import me.pceconomic.shop.domain.persona.SuperUsuari;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperUsuariRepository extends JpaRepository<SuperUsuari, Integer> {
    SuperUsuari findByPersona(Persona persona);
}
