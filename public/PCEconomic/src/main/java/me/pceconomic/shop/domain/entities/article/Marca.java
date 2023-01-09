package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;

import java.util.List;

@Entity
@Table(name = "marques")
public @Data class Marca {

    @Id @GeneratedValue
    @Column(name = "id_marca")
    private int id;

    @Column(name = "cif")
    private String cif;

    @Column(name = "nom")
    private String name;

}
