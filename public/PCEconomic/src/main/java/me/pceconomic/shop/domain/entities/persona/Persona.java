package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.pceconomic.shop.domain.entities.article.Carrito;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.article.factura.Factura;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "persones")
@Getter
@Setter
@NoArgsConstructor
public class Persona {

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

    @Column(name = "isActiu")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "id_carrito", referencedColumnName = "id_carrito", unique = true)
    private Carrito carrito;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Factura> factures;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_persona")
    private Set<Valoracions> valoracions;

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname1='" + surname1 + '\'' +
                ", surname2='" + surname2 + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telefon='" + telefon + '\'' +
                ", dni='" + dni + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

