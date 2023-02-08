package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.carrito.ShoppingCart;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Visita;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.forms.areaclients.AddDirectionForm;
import me.pceconomic.shop.repositories.VisitaRepository;
import me.pceconomic.shop.services.CarritoService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Controller
public class FrontController {

    private final FrontService frontService;
    private final CarritoService carritoService;
    private final VisitaRepository visitaRepository;

    @Autowired
    public FrontController(FrontService frontService, CarritoService carritoService,
                           VisitaRepository visitaRepository) {
        this.frontService = frontService;
        this.carritoService = carritoService;
        this.visitaRepository = visitaRepository;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "index";

        Client client = (Client) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");

        return "index";
    }

    @GetMapping("/article/{idArticle}/{idPropietat}")
    public String article(Model model, @PathVariable int idArticle, @PathVariable int idPropietat, HttpServletRequest request) {
        frontService.article(model, idArticle, idPropietat, request);
        Article article = frontService.getArticleRepository().findById(idArticle).orElse(null);
        Propietats propietats = frontService.getPropietatsRepository().findById(idPropietat).orElse(null);
        List<Visita> visites = visitaRepository.findAll();

        if (article == null || propietats == null) return "redirect:/error";




        article.getPropietats().forEach(prop -> {
            if (prop.getId() == propietats.getId()) {
                model.addAttribute("propietats", propietats);
                model.addAttribute("preuEuros", frontService.formatearComoEuros(prop.getPreu()));
                model.addAttribute("propietatsArticles", frontService.getPropietatsRepository().findAll());
            }
        });

        model.addAttribute("imatges", frontService.getImatgeRepository().findAll());

        return "article";
    }

    @GetMapping("/categoria/{id}")
    public String getCategories(Model model, @PathVariable int id, HttpServletRequest request) {
        frontService.getCategoria(model, id, request);
        return "categoria";
    }

    @GetMapping("/carrito/direccion")
    public String getDireccion(HttpServletRequest request, Model model) {
        if (servicePay(request, model)) return "index";
        return "direcciones-envio";
    }

    @GetMapping("/carrito/compra")
    public String getCompra(HttpServletRequest request, Model model) {
        if (servicePay(request, model)) return "index";
        return "compra";
    }

    private boolean servicePay(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return true;

        ShoppingCart shoppingCart = carritoService.getCarrito();
        System.out.println(shoppingCart);

        model.addAttribute("directionForm", new AddDirectionForm());

        Client client = (Client) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");
        return false;
    }

}
