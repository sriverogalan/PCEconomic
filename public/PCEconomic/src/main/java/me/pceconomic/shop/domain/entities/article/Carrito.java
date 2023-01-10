package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.persona.Client;

import java.util.List;

@Entity
public @Data class Carrito {
    @Id
    @GeneratedValue
    @Column(name = "id_carrito")
    private int id;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "preu")
    private double price;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

}
