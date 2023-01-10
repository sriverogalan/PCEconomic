package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "provincies")
@EqualsAndHashCode(exclude = {"ciutats"})
public @Data class Provincia {

    @Id @GeneratedValue
    @Column(name = "id_provincia")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_ciutat")
    private Set<Ciutat> ciutats;

}
