package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.factura.Factura;

import java.util.Set;

@Entity
@Table(name = "administradors")
@EqualsAndHashCode(exclude = {"articles", "factures"})
public @Data class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_administrador")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @OneToMany
    @JoinTable(name = "article-administrador", joinColumns = @JoinColumn(name = "id_administrador"), inverseJoinColumns = @JoinColumn(name = "id_article"))
    private Set<Article> articles;


}
