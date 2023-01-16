package me.pceconomic.shop.domain.entities.article.propietats;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Embeddable
@Table(name = "propietats_valor")
public @Data class PropietatsValor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietats_valor")
    private int id;

    @Column(name = "preu")
    private double preu;

    @Column(name = "stock")
    private int stock;

    @OneToMany
    @JoinColumn(name = "id_valor")
    private Set<Valor> valor;


}
