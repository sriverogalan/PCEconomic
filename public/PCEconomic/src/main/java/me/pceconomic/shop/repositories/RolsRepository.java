package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.persona.Rols;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolsRepository extends JpaRepository<Rols, Integer> {

    Rols getRolsByName(String name);

}
