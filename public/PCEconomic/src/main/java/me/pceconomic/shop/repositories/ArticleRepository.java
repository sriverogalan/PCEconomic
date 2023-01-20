package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.article.Article;
import me.pceconomic.shop.domain.article.categoria.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Set<Article> findBySubcategories(Subcategoria subcategoria);



}