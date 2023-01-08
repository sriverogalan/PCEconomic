package me.pceconomic.shop.domain.entities.categories;

import jakarta.persistence.*;
import lombok.Data;

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
