package me.pceconomic.shop.domain.entities.idioma;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "idioma")
@NoArgsConstructor
public @Data class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // Ex: 1

    @Column(name = "nom")
    private String nom; // Ex: Català

    @Column(name = "ruta")
    private String ruta; // Ex: /json/es_ES.json
}
