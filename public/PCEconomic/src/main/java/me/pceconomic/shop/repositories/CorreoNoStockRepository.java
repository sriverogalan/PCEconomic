package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.persona.CorreoNoStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorreoNoStockRepository extends JpaRepository<CorreoNoStock, Integer> {

    boolean existsByEmail(String email);

}
