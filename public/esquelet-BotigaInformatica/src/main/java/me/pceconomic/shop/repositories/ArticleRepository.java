package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}