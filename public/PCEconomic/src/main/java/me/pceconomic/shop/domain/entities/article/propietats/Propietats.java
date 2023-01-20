package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Imatge;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "propietats")
public @Data class Propietats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietats")
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @Column(name = "preu")
    private double preu;

    @Column(name = "stock")
    private int stock;

    @Column(name = "esPrincipal")
    private boolean esPrincipal;

    @OneToMany
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "id_propietats_valor")
    private Set<Valor> valor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_propietats")
    private Set<Imatge> imatges;
}