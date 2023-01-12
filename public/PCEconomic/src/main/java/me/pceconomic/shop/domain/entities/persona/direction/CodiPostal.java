package me.pceconomic.shop.domain.entities.persona.direction;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "codis_postals")
public @Data class CodiPostal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_codipostal")
    private int id;

    @Column(name = "codi")
    private String code;

}
