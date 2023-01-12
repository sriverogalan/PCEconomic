package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "imatges")
public @Data class Imatge {

    @Id @GeneratedValue
    @Column(name = "id_imatge")
    private int id;

    @Column(name = "path")
    private String path;
}
