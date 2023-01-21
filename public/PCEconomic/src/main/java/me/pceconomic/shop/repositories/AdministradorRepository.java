package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.persona.Administrador;
import me.pceconomic.shop.domain.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {

   Administrador findByPersona(Persona persona);

}
