package me.pceconomic.shop.domain.entities.idiomes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Oferta;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

import java.util.Set;

@Entity
@Table(name = "idiomes")
@EqualsAndHashCode(exclude = {"valores"})
public @Data class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_idioma")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany(mappedBy = "idioma", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ValorIdioma> valores;

    @OneToMany(mappedBy = "idioma", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Estatic> estatics;

    @ManyToMany
    @JoinTable(name = "idioma_articles",
            joinColumns = @JoinColumn(name = "id_idioma"),
            inverseJoinColumns = @JoinColumn(name = "id_article"))
    private Set<Article> articles;

    @ManyToMany
    @JoinTable(name = "idioma_propietats",
            joinColumns = @JoinColumn(name = "id_idioma"),
            inverseJoinColumns = @JoinColumn(name = "id_propietats"))
    private Set<Propietats> propietats;

    @ManyToMany
    @JoinTable(name = "idioma_ofertes",
            joinColumns = @JoinColumn(name = "id_idioma"),
            inverseJoinColumns = @JoinColumn(name = "id_ofertes"))
    private Set<Oferta> ofertes;
}