package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.propietats.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {


}
