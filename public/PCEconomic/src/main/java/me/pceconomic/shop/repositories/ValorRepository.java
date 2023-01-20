package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.article.propietats.Valor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValorRepository extends JpaRepository<Valor, Integer> {

}
