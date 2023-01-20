package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.article.factura.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

}
