package me.pceconomic.shop.domain.entities.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "articles")
@EqualsAndHashCode(exclude = {"propietats", "subcategories", "marca"})
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article")
    private int id;

    @Column(name = "nom")
    private String nom;

    @Lob
    @JsonIgnore
    @Column(name = "descripcio", columnDefinition = "LONGTEXT")
    private String descripcio;

    @Column(name = "pes")
    private double pes;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "articles_subcategories",
            joinColumns = @JoinColumn(name = "id_article"),
            inverseJoinColumns = @JoinColumn(name = "id_subcategoria")
    )
    private Set<Subcategoria> subcategories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_article")
    @JsonIgnore
    private Set<Propietats> propietats;

}
