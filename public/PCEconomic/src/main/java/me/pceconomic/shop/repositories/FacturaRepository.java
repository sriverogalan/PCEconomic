package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.factura.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

}
