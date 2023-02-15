package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "imatges")
@NoArgsConstructor
public @Data class Imatge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imatge")
    private int id;

    @Column(name = "path")
    private String path;

    @Column(name = "principal")
    private boolean principal;

}
