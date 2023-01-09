package me.pceconomic.shop.domain.entities.categories;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;

import java.util.List;

@Entity
@Table(name = "categories")
public @Data class Categoria {

    @Id
    @GeneratedValue
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nom")
    private String name;

    @ManyToOne
    @JoinColumn(name = "pare")
    private Categoria parent;

    @OneToMany(mappedBy = "parent")
    private List<Categoria> children;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article articles;

}
