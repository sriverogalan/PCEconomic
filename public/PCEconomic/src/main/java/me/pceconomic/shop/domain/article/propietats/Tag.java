package me.pceconomic.shop.domain.article.propietats;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private int id;

    @Column(name = "nom")
    private String tag;

    @OneToOne
    @JoinColumn(name = "id_valor", referencedColumnName = "id_valor")
    private Valor valor;

}
