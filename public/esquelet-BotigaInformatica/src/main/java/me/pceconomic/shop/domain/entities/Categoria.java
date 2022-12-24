package me.pceconomic.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class Categoria {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "nom")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

}
