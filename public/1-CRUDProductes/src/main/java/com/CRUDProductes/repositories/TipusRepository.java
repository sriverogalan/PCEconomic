package com.CRUDProductes.repositories;

import com.CRUDProductes.models.Tipus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipusRepository extends JpaRepository<Tipus, Long> {
}
