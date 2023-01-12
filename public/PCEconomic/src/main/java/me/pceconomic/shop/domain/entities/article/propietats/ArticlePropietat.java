package me.pceconomic.shop.domain.entities.article.propietats;


import jakarta.persistence.*;
import me.pceconomic.shop.domain.entities.article.Article;

@Entity
@Table(name = "article_propietats")
public class ArticlePropietat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article_propietat")
    private int id;

    @Column(name = "preu")
    private double preu;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "id_propietats")
    private Propietats propietats;

}
