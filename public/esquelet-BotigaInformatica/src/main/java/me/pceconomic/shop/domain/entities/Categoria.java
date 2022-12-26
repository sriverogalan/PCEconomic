package me.pceconomic.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "categories")
public @Data class Categoria {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_subcategoria")
    private List<Subcategoria> subcategories;

    @ManyToOne
    @JoinColumn(name = "id")
    private Article article;

}
