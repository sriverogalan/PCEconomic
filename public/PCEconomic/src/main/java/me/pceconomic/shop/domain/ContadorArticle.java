package me.pceconomic.shop.domain;

import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;

public @Data class ContadorArticle {
    private int idArticle;
    private int contador = 0;
}
