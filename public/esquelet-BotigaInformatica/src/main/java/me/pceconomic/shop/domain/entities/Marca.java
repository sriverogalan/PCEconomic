package me.pceconomic.shop.domain.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "marques")
public @Data class Marca {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "cif")
    private String cif;

    @Column(name = "nom")
    private String name;

    @Column(name = "id_article")
    @OneToMany
    private List<Article> article;


}
