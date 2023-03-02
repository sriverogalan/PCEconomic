package me.pceconomic.shop.domain.entities.article.categoria;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(exclude = "subcategorias")
public @Data class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nom")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_categoria")
    private Set<Subcategoria> subcategorias;

}
