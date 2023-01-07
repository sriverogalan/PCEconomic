package me.pceconomic.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "articles")
public @Data class Article {

    @Id @GeneratedValue
    @Column(name = "id_article")
    private int id;

    @Column(name = "nom")
    private String name;

    @Column(name = "descripcio")
    private String description;

    @Column(name = "stockTotal")
    private double totalStock;

    @Column(name = "pes")
    private double weight;

    @OneToMany
    @JoinColumn(name = "id_categoria")
    private List<Categoria> categories;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marques;

}
