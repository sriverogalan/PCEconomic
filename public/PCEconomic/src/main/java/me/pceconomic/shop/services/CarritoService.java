package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.propietats.Propietat;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.repositories.PropietatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {

    private final HttpSession session;
    private final PropietatsRepository propietatsRepository;

    @Autowired
    public CarritoService(HttpSession session, PropietatsRepository propietatsRepository) {
        this.session = session;
        this.propietatsRepository = propietatsRepository;
    }

    public void addArticleToCart() {
        List<Propietats> articles = new ArrayList<>(propietatsRepository.findAll());
        session.setAttribute("article", articles);
    }

    public List<Propietats> getArticlesFromCart() {
        return (List<Propietats>) session.getAttribute("article");
    }



}
