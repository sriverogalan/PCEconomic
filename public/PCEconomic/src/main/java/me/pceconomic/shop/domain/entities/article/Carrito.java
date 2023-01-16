package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(exclude = {"articlePropietats"})
public @Data class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private int id;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "preu")
    private double price;

}
