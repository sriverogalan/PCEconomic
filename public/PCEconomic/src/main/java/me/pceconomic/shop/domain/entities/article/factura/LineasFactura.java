package me.pceconomic.shop.domain.entities.article.factura;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.pceconomic.shop.domain.entities.article.Marca;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class LineasFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lineaFactura")
    private int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_factura")
    private Factura factura;

    @Column(name = "nom_article")
    private String nomArticle;


    @JoinColumn(name = "propietats")
    private String propietats;

    @Column(name = "preu")
    private double price;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "esValorat")
    private boolean esValorat;

    @Column(name = "id_props")
    private int idPropietats;

    @Column(name = "id_art")
    private int idArticle;

    @OneToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;



    @Override
    public String toString() {
        return "LineasFactura{" +
                "id=" + id +
                ", nomArticle='" + nomArticle + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", esValorat=" + esValorat +
                '}';
    }
}
