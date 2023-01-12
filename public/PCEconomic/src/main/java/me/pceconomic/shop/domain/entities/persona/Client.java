package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.Carrito;
import me.pceconomic.shop.domain.entities.article.factura.Factura;

import java.util.Set;

@Entity
@Table(name = "clients")
@EqualsAndHashCode(exclude = "factures")
public @Data class Client {

    @Id
    @GeneratedValue
    @Column(name = "id_client")
    private int id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "isSuscrit")
    private boolean isSubscribed;

    @Column(name = "isActiu")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @OneToOne
    @JoinColumn(name = "id_carrito", referencedColumnName = "id_carrito")
    private Carrito carrito;

    @OneToMany
    private Set<Factura> factures;

}
