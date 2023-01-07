package me.pceconomic.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "subcategories")
public @Data class Subcategoria {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "nom")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

}
