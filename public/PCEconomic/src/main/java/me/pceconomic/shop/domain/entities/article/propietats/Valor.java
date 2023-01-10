package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "valors")
public @Data class Valor {

    @Id @GeneratedValue
    @Column(name = "id_valor")
    private int id;

    @Column(name = "clau")
    private String clau;

}