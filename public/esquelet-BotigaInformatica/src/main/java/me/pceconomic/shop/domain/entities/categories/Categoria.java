package me.pceconomic.shop.domain.entities.categories;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.Article;

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
    @JoinColumn(name = "id_article")
    private Article article;

}
