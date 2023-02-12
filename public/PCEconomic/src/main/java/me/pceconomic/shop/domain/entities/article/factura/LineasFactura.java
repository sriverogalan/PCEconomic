package me.pceconomic.shop.domain.entities.article.factura;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class LineasFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lineaFactura")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

    @Column(name = "numero_factura")
    private int numeroFactura;

    @Column(name = "nom_article")
    private String nomArticle;

    @Column(name = "nom_propietats")
    private String nomPropietats;

    @Column(name = "preu")
    private double price;

    @Column(name = "quantitat")
    private int quantity;


}
