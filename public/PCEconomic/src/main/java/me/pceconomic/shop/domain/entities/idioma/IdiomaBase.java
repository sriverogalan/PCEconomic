package me.pceconomic.shop.domain.entities.idioma;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "idiomaBase")
@NoArgsConstructor
public @Data class IdiomaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "clau")
    private String clau;

    @Column(name = "valor")
    private String valor;

    @Column(name = "pagina")
    private String pagina;

}
