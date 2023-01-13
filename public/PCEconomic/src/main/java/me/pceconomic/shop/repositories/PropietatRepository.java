package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.propietats.Propietat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropietatRepository extends JpaRepository<Propietat, Integer> {

}