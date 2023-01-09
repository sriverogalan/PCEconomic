package me.pceconomic.shop.domain.entities.idiomes;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
public @Data class Idioma {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_idioma")
    private int id;
    @OneToMany(mappedBy="idioma", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<ValorIdioma> valores;
}