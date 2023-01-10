package me.pceconomic.shop.domain.entities.idiomes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "idiomes")
@EqualsAndHashCode(exclude = {"valores"})
public @Data class Idioma {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_idioma")
    private int id;
    @OneToMany(mappedBy="idioma", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Set<ValorIdioma> valores;
}