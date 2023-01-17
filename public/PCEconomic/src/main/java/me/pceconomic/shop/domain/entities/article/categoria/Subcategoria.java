package me.pceconomic.shop.domain.entities.article.categoria;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subcategories")
public @Data class Subcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategoria")
    private int id;

    @Column(name = "nom")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

}
