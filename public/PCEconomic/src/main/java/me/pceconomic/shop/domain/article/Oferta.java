package me.pceconomic.shop.domain.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.article.propietats.Propietats;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "ofertes")
@EqualsAndHashCode(exclude = {"articles"})
public @Data class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta")
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_inici")
    private LocalDate dataInici;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_fi")
    private LocalDate dataFi;

    @Column(name = "nom")
    private String name;

    @Column(name = "tipus")
    private String type;

    @Column(name = "descompte")
    private double descompte;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "ofertes_propietats",
            joinColumns = @JoinColumn(name = "id_oferta"),
            inverseJoinColumns = @JoinColumn(name = "id_propietats")
    )
    private Set<Propietats> propietats;

    @ManyToMany
    @JoinTable(
            name = "ofertes_articles",
            joinColumns = @JoinColumn(name = "id_oferta"),
            inverseJoinColumns = @JoinColumn(name = "id_article")
    )
    private Set<Article> articles;
}