package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class Rols {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private int id;

    @Column(name = "name")
    private String name;
}
