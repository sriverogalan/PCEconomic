package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Categoria findByName(String name);

}
