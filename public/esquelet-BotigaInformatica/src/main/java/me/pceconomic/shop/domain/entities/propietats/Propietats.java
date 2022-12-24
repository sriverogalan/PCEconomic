package me.pceconomic.shop.domain.entities.propietats;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.Article;

import java.util.List;

@Entity
@Table(name = "propietats")
public @Data class Propietats {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "id_article")
    private List<Article> articles;
}
