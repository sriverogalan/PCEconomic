package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Set<Article> findByCategories(Categoria categoria);

}