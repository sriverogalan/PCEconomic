package com.CRUDProductes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
public @Data class DetallProducte {

    @Id
    @GeneratedValue
    private Long id;

}
