package me.pceconomic.shop.domain.entities.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "marques")
@EqualsAndHashCode(exclude = "articles")
@NoArgsConstructor
@Getter
@Setter
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    private int id;

    @Column(name = "cif")
    private String cif;

    @Column(name = "nom")
    private String name;

    @Column(name = "isActiu")
    private boolean isActive;

    @OneToMany(mappedBy = "marca")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<Article> articles;

    public Marca(String cif, String name, Set<Article> articles) {
        this.cif = cif;
        this.name = name;
        this.articles = articles;
    }


}
