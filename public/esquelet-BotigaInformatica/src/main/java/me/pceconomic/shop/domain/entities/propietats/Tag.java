package me.pceconomic.shop.domain.entities.propietats;

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

    @OneToMany
    @JoinColumn(name = "id_valor")
    private List<Valor> valor;

}
