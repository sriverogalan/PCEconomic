package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.forms.AddValorationForm;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@Getter
public class FrontService {

    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final ArticleRepository articleRepository;
    private final ImatgeRepository imatgeRepository;
    private final PropietatsRepository propietatsRepository;
    private final VisitaRepository visitaRepository;
    private final ValoracionsRepository valoracionsRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public FrontService(VisitaRepository visitaRepository, ValoracionsRepository valoracionsRepository,ClientRepository clientRepository, SubcategoriaRepository subcategoriaRepository, PropietatsRepository propietatsRepository, CategoriaRepository categoriaRepository, ImatgeRepository imatgeRepository, ArticleRepository articleRepository) {
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.imatgeRepository = imatgeRepository;
        this.propietatsRepository = propietatsRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.visitaRepository = visitaRepository;
        this.valoracionsRepository = valoracionsRepository;
        this.clientRepository = clientRepository;
    }

    public void article(Model model, HttpServletRequest request) {
        sendListsToView(model, request);
    }

    public String formatearComoEuros(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        return formatter.format(value);
    }

    public List<Valoracions> getValoracionsPerArticle(int idArticle) {
        return valoracionsRepository.findAllByArticleId(idArticle);
    }

    public String getFechaValoracion(int idArticle) {
        List<Valoracions> valoracions = this.getValoracionsPerArticle(idArticle);

        if (valoracions.isEmpty()) return "";

        String fecha = "";

        for (Valoracions valoracion : valoracions)
            fecha = valoracion.getData().toString();


        System.out.println(fecha);
        return fecha;
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
        model.addAttribute("user", client);
    }

    public void addValoracio(int idClient, int idArticle, AddValorationForm valorationForm) {
        Valoracions valoracions = new Valoracions();
        Article article = articleRepository.findById(idArticle).orElse(null);
        Client client = clientRepository.findById(idClient).orElse(null);

        valoracions.setValoracio(valorationForm.getValoracio());
        valoracions.setComentari(valorationForm.getComentari());
        valoracions.setArticle(article);
        valoracions.setClient(client);
        valoracions.setData(LocalDate.now());

        valoracionsRepository.save(valoracions);
    }

    public void updateValoracio(int idValoracio, AddValorationForm valorationForm) {
        Valoracions valoracions = valoracionsRepository.findById(idValoracio).orElse(null);

        if (valoracions == null) return;

        valoracions.setValoracio(valorationForm.getValoracio());
        valoracions.setComentari(valorationForm.getComentari());
        valoracions.setData(LocalDate.now());

        valoracionsRepository.save(valoracions);
    }

    public void deleteValoracio(int idValoracio) {
        valoracionsRepository.deleteById(idValoracio);
    }


}
