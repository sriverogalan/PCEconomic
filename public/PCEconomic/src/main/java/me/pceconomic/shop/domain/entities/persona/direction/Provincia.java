package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "provincies")
public @Data class Provincia {

    @Id @GeneratedValue
    @Column(name = "id_provincia")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_ciutat")
    private List<Ciutat> ciutats;

}
