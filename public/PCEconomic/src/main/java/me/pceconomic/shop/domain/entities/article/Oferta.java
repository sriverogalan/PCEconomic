package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @JoinColumn(name = "id_article")
    private Set<Article> articles;
}