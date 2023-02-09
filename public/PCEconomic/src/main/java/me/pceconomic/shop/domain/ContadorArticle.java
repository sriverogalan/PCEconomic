package me.pceconomic.shop.domain;

import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;

import java.time.LocalDateTime;

public @Data class ContadorArticle {
    private int idArticle;
    private int idPropietat;
    private int contador = 0;
    private LocalDateTime data = LocalDateTime.now();
}
