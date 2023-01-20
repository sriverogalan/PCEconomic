package me.pceconomic.shop.domain.idiomes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class Estatic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ruta")
    private String ruta;

    @ManyToOne
    @JoinColumn(name = "id_idioma")
    private Idioma idioma;
}
