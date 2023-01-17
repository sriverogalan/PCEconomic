package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

@Entity
@Table(name = "imatges")
@NoArgsConstructor
public @Data class Imatge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imatge")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_propietats")
    private Propietats propietats;

    @Column(name = "path")
    private String path;

    public Imatge(String path) {
        this.path = path;
    }
}
