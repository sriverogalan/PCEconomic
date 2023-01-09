package me.pceconomic.shop.domain.entities.propietats;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stock")
public @Data class Stock {

    @Id @GeneratedValue
    @Column(name = "id_stock")
    private int id;

    @Column(name = "stock")
    private int stock;

}
