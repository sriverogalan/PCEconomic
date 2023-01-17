package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Integer> {

}
