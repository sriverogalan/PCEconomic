package me.pceconomic.shop.domain.entities.article.categoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "subcategories")
@EqualsAndHashCode(exclude = "categoria")
public @Data class Subcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subcategoria")
    private int id;

    @Column(name = "nom")
    private String name;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

}
