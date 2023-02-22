package me.pceconomic.shop.domain.entities.article.factura;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Marca;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

@Entity
public @Data class LineasFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lineaFactura")
    private int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_factura")
    private Factura factura;

    @Column(name = "nom_article")
    private String nomArticle;


    @JoinColumn(name = "id_propietat")
    @OneToOne
    private Propietats propietats;

    @Column(name = "preu")
    private double price;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "esValorat")
    private boolean esValorat;

    @OneToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

}
