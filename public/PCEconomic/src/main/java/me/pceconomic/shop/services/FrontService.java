package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import me.pceconomic.shop.domain.entities.article.factura.LineasFactura;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.AddValorationForm;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final PersonaRepository personaRepository;
    private final LineaFacturaRepository lineasFacturaRepository;

    @Autowired
    public FrontService(VisitaRepository visitaRepository, PersonaRepository personaRepository, ValoracionsRepository valoracionsRepository, SubcategoriaRepository subcategoriaRepository, PropietatsRepository propietatsRepository, CategoriaRepository categoriaRepository, ImatgeRepository imatgeRepository, ArticleRepository articleRepository, LineaFacturaRepository lineasFacturaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.imatgeRepository = imatgeRepository;
        this.propietatsRepository = propietatsRepository;
        this.subcategoriaRepository = subcategoriaRepository;
        this.visitaRepository = visitaRepository;
        this.valoracionsRepository = valoracionsRepository;
        this.personaRepository = personaRepository;
        this.lineasFacturaRepository = lineasFacturaRepository;
    }

    public void article(Model model, HttpServletRequest request) {
        sendListsToView(model, request);
    }

    public String formatearComoEuros(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        return formatter.format(value);
    }

    public List<Valoracions> getValoracionsPerArticle(Article article) {
        List<Valoracions> valoracions = new ArrayList<>();
        for (Valoracions valoracio : valoracionsRepository.findAll()) {
            if (valoracio.getPropietats().getArticle().getId() == article.getId()) {
                valoracions.add(valoracio);
            }
        }
        valoracions.sort(Comparator.comparing(Valoracions::getData).reversed());
        return valoracions;
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

        Persona client = (Persona) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");
        model.addAttribute("user", client);
        model.addAttribute("rols", client == null ? null : client.getRols());

    }

    @Transactional
    public void addValoracio(HttpSession session, int idArticle, AddValorationForm valorationForm, int idPropietat, int idLinea) {
        Valoracions valoracions = new Valoracions();
        Article article = articleRepository.findById(idArticle).orElse(null);
        Persona sessionPersona = (Persona) session.getAttribute("persona");
        Propietats propietats = propietatsRepository.findById(idPropietat).orElse(null);
        LineasFactura lineasFactura = lineasFacturaRepository.findById(idLinea).orElse(null);

        Persona persona = personaRepository.findById(sessionPersona.getId()).orElse(null);

        if (article == null || persona == null || propietats == null) return;

        valoracions.setValoracio(valorationForm.getValoracio());
        valoracions.setComentari(valorationForm.getComentari());
        valoracions.setData(LocalDate.now());
        valoracions.setPropietats(propietats);
        valoracions.setIdPersona(persona.getId());

        valoracionsRepository.save(valoracions);

        if (lineasFactura == null) return;

        lineasFactura.setEsValorat(true);
        lineasFacturaRepository.save(lineasFactura);
        this.setSession(session, persona);
    }

    public void updateValoracio(int idValoracio, AddValorationForm valorationForm) {
        Valoracions valoracions = valoracionsRepository.findById(idValoracio).orElse(null);

        if (valoracions == null) return;
        if (valorationForm.getValoracio() == 0.0 && valorationForm.getComentari() == null) return;
        if (valorationForm.getValoracio() == 0.0) return;
        if (valorationForm.getComentari() == null) return;
        if (valorationForm.getComentari().equals("")) return;

        valoracions.setValoracio(valorationForm.getValoracio());
        valoracions.setComentari(valorationForm.getComentari());
        valoracions.setData(LocalDate.now());

        valoracionsRepository.save(valoracions);
    }

    public void deleteValoracio(int idValoracio) {
        valoracionsRepository.deleteById(idValoracio);
    }

    public void setSession(HttpSession session, Persona persona) {
        session.setAttribute("persona", persona);
        session.setAttribute("direccions", persona.getDireccions());
        session.setAttribute("pedidos", persona.getFactures());
    }

}