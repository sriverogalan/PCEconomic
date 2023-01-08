package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "administradors")
public @Data class Administrador {

    @Id
    @GeneratedValue
    @Column(name = "id_administrador")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;
}
