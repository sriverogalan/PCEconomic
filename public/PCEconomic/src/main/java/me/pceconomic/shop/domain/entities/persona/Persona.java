package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Carrito;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.article.factura.Factura;

import java.util.Set;

@Entity
@Table(name = "persones")
@EqualsAndHashCode(exclude = {"direccions"})
public @Data class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private int id;

    @Column(name = "nom")
    private String name;

    @Column(name = "cognom1")
    private String surname1;

    @Column(name = "cognom2")
    private String surname2;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "contrasenya", unique = true, nullable = false)
    private String password;

    @Column(name = "telefon", unique = true, nullable = false)
    private String telefon;

    @Column(name = "dni")
    private String dni;

    @Column(name = "isSuscrit")
    private boolean isSubscribed;

    @Column(name = "isActiu")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "id_carrito", referencedColumnName = "id_carrito", unique = true)
    private Carrito carrito;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Factura> factures;

    @OneToOne
    private Valoracions valoracio;

    @OneToMany
    @JoinTable(name = "article-administrador", joinColumns = @JoinColumn(name = "id_administrador"), inverseJoinColumns = @JoinColumn(name = "id_article"))
    private Set<Article> articles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "persones_direccions",
            joinColumns = @JoinColumn(name = "id_persona"),
            inverseJoinColumns = @JoinColumn(name = "id_direccio")
    )
    private Set<Direccio> direccions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "persones_rols",
            joinColumns = @JoinColumn(name = "id_persona"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rols> rols;
}

