package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    Persona findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByTelefon(String telefon);

}
