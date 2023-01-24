package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

}
