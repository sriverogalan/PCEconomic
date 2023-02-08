package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.Valoracions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValoracionsRepository extends JpaRepository<Valoracions, Integer> {

    List<Valoracions> findAllByArticleId(int idArticle);

}
