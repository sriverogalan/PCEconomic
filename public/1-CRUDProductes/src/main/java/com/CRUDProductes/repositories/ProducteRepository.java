package com.CRUDProductes.repositories;

import com.CRUDProductes.models.Producte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducteRepository extends JpaRepository<Producte, Long> {

}
