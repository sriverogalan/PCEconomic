package me.pceconomic.shop.translations;

import com.google.cloud.translate.Language;
import me.pceconomic.shop.translations.objects.TranslationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

public interface ITranslateInterface {

    @POST("/piano/nologin/google/translate/languages")
    Call<List<Language>> languageList();

    @POST("/piano/nologin/google/translate/auto")
    Call<TranslationResponse> translate(@Body String json);
}
