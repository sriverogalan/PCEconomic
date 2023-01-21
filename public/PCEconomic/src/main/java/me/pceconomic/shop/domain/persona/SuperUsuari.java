package me.pceconomic.shop.domain.persona;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "superusuaris")
public @Data class SuperUsuari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_superusuari")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;
}
