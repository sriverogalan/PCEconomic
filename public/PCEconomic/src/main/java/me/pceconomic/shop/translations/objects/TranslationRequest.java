package me.pceconomic.shop.translations.objects;

import lombok.Data;

public @Data class TranslationRequest {

    private String languageTo;
    private String text;

    public TranslationRequest(String languageTo, String text) {
        this.languageTo = languageTo;
        this.text = text;
    }

}
