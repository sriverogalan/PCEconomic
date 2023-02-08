package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.NumberFormat;
import java.util.Locale;

@Service
@Getter
public class FrontService {

    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final ArticleRepository articleRepository;
    private final ImatgeRepository imatgeRepository;
    private final PropietatsRepository propietatsRepository;
    private final ValoracionsRepository valoracionsRepository;

    @Autowired
    public FrontService(ValoracionsRepository valoracionsRepository, SubcategoriaRepository subcategoriaRepository, PropietatsRepository propietatsRepository, CategoriaRepository categoriaRepository, ImatgeRepository imatgeRepository, ArticleRepository articleRepository) {
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.imatgeRepository = imatgeRepository;
        this.propietatsRepository = propietatsRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.valoracionsRepository = valoracionsRepository;
    }

    public void article(Model model, int idArticle, int idPropietat, HttpServletRequest request) {


        sendListsToView(model, request);
    }

    public String formatearComoEuros(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        return formatter.format(value);
    }


    public void getCategoria(Model model, int id, HttpServletRequest request) {
        Subcategoria subcategoria = subcategoriaRepository.findById(id).orElse(null);

        if (subcategoria == null) return;

        model.addAttribute("subcategoria", subcategoria);
        sendListsToView(model, request);
    }

    public void sendListsToView(Model model, HttpServletRequest request) {
        model.addAttribute("categories", categoriaRepository.findAll());
        model.addAttribute("subcategories", subcategoriaRepository.findAll());
        model.addAttribute("imatges", imatgeRepository.findAll());

        HttpSession session = request.getSession();

        if (session == null) return;

        Client client = (Client) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");
    }

    public void getDireccion(Model model, HttpServletRequest request) {
        sendListsToView(model, request);
    }
}
