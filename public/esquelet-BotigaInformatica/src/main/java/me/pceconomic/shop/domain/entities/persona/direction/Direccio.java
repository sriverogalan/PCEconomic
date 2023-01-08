package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;

import java.util.List;

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

    @Column(name = "estaActiva")
    private boolean isActive;

    @OneToMany
    @JoinColumn(name = "id_pais")
    private List<Country> country;
}
