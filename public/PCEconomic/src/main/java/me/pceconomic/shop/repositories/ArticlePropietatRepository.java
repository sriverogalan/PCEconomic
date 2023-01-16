package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.propietats.PropietatsValor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlePropietatRepository extends JpaRepository<PropietatsValor, Integer> {

}
