package me.pceconomic.shop.domain.entities.idiomes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class Clau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clau")
    private int id;

    @OneToOne(mappedBy = "clau")
    private ValorIdioma valor;
}
