package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Imatge;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "propietats")
@EqualsAndHashCode(exclude = {"article", "valor", "imatges"})
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

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "id_propietats_valor")
    private Set<Valor> valor;

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "id_propietats")
    private Set<Imatge> imatges;
}
