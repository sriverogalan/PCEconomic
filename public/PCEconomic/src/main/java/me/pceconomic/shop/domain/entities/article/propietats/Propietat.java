package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "propietat")
@EqualsAndHashCode(exclude = "propietats")
public @Data class Propietat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietat")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_propietat")
    private Set<Valor> valor;

    @OneToMany
    @JoinColumn(name = "id_propietat")
    private Set<Propietats> propietats;

}
