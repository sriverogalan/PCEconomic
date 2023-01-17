package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PropietatsRepository extends JpaRepository<Propietats, Integer> {

}
