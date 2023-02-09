package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitaRepository extends JpaRepository<Visita, Integer> {
    List<Visita> findAllByClientId(int id);

}
