package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "propietat")
@EqualsAndHashCode(exclude = "valors")
public @Data class Propietat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietat")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany(mappedBy = "propietat", cascade = CascadeType.ALL)
    private Set<Valor> valors;
}
