package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {

    @Id @GeneratedValue
    @Column(name = "id_tag")
    private int id;

    @Column(name = "nom")
    private String tag;

    @OneToOne
    @JoinColumn(name = "id_valor", referencedColumnName = "id_valor")
    private Valor valor;

}
