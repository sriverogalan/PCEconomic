package me.pceconomic.shop.translations;

import com.google.cloud.translate.Language;
import com.google.gson.Gson;
import me.pceconomic.shop.translations.objects.TranslationRequest;
import me.pceconomic.shop.translations.objects.TranslationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

@Component
public class TranslationManager {

    private final Gson gson;
    private final ITranslateInterface ITranslateInterface;

    @Autowired
    public TranslationManager(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://theteacher.codiblau.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.gson = gson;
        ITranslateInterface = retrofit.create(ITranslateInterface.class);
    }

    public List<Language> getTranslationList() {
        try {
            Call<List<Language>> langList = ITranslateInterface.languageList();
            Response<List<Language>> response = langList.execute();

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String translate(String text, String targetLanguage) {
        try {
            String json = gson.toJson(new TranslationRequest(text, targetLanguage));
            Call<TranslationResponse> translation = ITranslateInterface.translate(json);
            Response<TranslationResponse> response = translation.execute();

            assert response.body() != null;
            return response.body().getTranslation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
