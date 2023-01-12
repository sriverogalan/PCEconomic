package me.pceconomic.shop.domain.entities.article.factura;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class LineasFactura {
    @Id
    @GeneratedValue
    @Column(name = "id_lineaFactura")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

}
