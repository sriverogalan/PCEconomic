package com.CRUDProductes.repositories;

import com.CRUDProductes.models.DetallProducte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallProducteRepository extends JpaRepository<DetallProducte, Long> {
}
