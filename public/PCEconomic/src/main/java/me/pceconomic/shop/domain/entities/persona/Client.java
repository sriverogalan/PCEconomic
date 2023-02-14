package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.Carrito;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.article.factura.Factura;

import java.util.Set;

@Entity
@Table(name = "clients")
@EqualsAndHashCode(exclude = "factures")
public @Data class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private int id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "isSuscrit")
    private boolean isSubscribed;

    @Column(name = "isActiu")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "id_persona", unique = true, nullable = false)
    private Persona persona;

    @OneToOne
    @JoinColumn(name = "id_carrito", referencedColumnName = "id_carrito", unique = true)
    private Carrito carrito;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Factura> factures;

    @OneToOne
    private Valoracions valoracio;
}
