package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;

@Entity
@Table(name = "direccions")
public class Direccio {
    @Id @GeneratedValue
    @Column(name = "id_direccio")
    private int id;

    @Column(name = "carrer")
    private String street;

    @Column(name = "numero")
    private int number;

    @Column(name = "poblacio")
    private String city;

    @Column(name = "provincia")
    private String province;

    @Column(name = "codiPostal")
    private String postalCode;

    @Column(name = "pais")
    private String country;

    @Column(name = "estaActiva")
    private boolean isActive;
}
