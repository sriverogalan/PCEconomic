package me.pceconomic.shop.domain.idiomes;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class ValorIdioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valor")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idioma_id")
    private Idioma idioma;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_clau")
    private Clau clau;
}
