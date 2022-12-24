package me.pceconomic.shop.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
public @Data class Subcategoria extends Categoria{
    @ManyToOne
    @JoinColumn(name = "id_subcategoria")
    private Categoria categoria;

}
