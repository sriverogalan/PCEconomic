package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "direccions")
public @Data class Direccio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccio")
    private int id;

    @Column(name = "carrer")
    private String street;

    @Column(name = "numero")
    private int number;

    @Column(name = "estaActiva")
    private boolean isActive;

    @Column(name = "ciutat")
    private String city;

    @Column(name = "provincia")
    private String province;

    @Column(name = "codi_postal")
    private String postalCode;

    @Column(name = "pais")
    private String country;

    @Column(name = "comunitat_autonoma")
    private String autonomousCommunity;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;
}
