package me.pceconomic.shop.domain;

import lombok.Data;

import java.time.LocalDateTime;

public @Data class ContadorArticle {
    private int idArticle;
    private int idPropietat;
    private int contador = 0;
    private LocalDateTime data = LocalDateTime.now();
}
