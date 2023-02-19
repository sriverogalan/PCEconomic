package me.pceconomic.shop.translations.objects;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class TranslationResponse {

    @SerializedName("traduccio")
    private String translation;

    @SerializedName("idiomaoriginal")
    private String originalLanguage;

}
