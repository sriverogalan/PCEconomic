package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;

import java.util.List;

@Entity
@Table(name = "administradors")
public @Data class Administrador {

    @Id @GeneratedValue
    @Column(name = "id_administrador")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @OneToMany
    @JoinTable(name = "article-administrador", joinColumns = @JoinColumn(name = "id_administrador"), inverseJoinColumns = @JoinColumn(name = "id_article"))
    private List<Article> articles;
}
