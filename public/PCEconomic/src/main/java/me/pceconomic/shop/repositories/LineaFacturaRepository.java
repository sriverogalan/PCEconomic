package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.article.factura.LineasFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineaFacturaRepository extends JpaRepository<LineasFactura, Integer> {
}
