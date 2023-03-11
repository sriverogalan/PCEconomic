package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.persona.CorreoNoStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorreoRepository extends JpaRepository<CorreoNoStock, Integer> {

    boolean existsByEmail(String email);

}
