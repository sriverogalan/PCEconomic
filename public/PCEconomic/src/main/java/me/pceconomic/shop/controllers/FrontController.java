package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.ContadorArticle;
import me.pceconomic.shop.domain.carrito.Cart;
import me.pceconomic.shop.domain.carrito.ShoppingCart;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.factura.Factura;
import me.pceconomic.shop.domain.entities.article.factura.LineasFactura;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.forms.AddValorationForm;
import me.pceconomic.shop.domain.forms.areaclients.AddDirectionForm;
import me.pceconomic.shop.repositories.FacturaRepository;
import me.pceconomic.shop.repositories.LineaFacturaRepository;
import me.pceconomic.shop.repositories.VisitaRepository;
import me.pceconomic.shop.services.CarritoService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class FrontController {

    private final FrontService frontService;
    private final CarritoService carritoService;
    private final VisitaRepository visitaRepository;
    private final FacturaRepository facturaRepository;
    private final LineaFacturaRepository lineaFacturaRepository;

    @Autowired
    public FrontController(FrontService frontService, LineaFacturaRepository lineaFacturaRepository, CarritoService carritoService, VisitaRepository visitaRepository, FacturaRepository facturaRepository) {
        this.frontService = frontService;
        this.carritoService = carritoService;
        this.visitaRepository = visitaRepository;
        this.facturaRepository = facturaRepository;
        this.lineaFacturaRepository = lineaFacturaRepository;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "index";

        Client client = (Client) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");
        model.addAttribute("user", client);

        return "index";
    }

    @GetMapping("/article/{idArticle}/{idPropietat}")
    public String article(Model model, @PathVariable int idArticle, @PathVariable int idPropietat, HttpServletRequest request) {
        frontService.article(model, request);
        Article article = frontService.getArticleRepository().findById(idArticle).orElse(null);
        Propietats propietats = frontService.getPropietatsRepository().findById(idPropietat).orElse(null);

        if (article == null || propietats == null) return "redirect:/error";

        HttpSession session = request.getSession();
        if (session == null) return "redirect:/";

        List<ContadorArticle> contadors = (List<ContadorArticle>) session.getAttribute("contadors");

        if (contadors == null) {
            contadors = new ArrayList<>();
        }

        boolean existeix = false;
        for (ContadorArticle contador : contadors) {
            if (contador.getIdArticle() == idArticle) {
                contador.setContador(contador.getContador() + 1);
                contador.setIdPropietat(idPropietat);
                contador.setData(LocalDateTime.now());
                existeix = true;
            }
        }
        if (!existeix) {
            ContadorArticle contador = new ContadorArticle();
            contador.setIdArticle(idArticle);
            contador.setIdPropietat(idPropietat);
            contador.setContador(1);
            contador.setData(LocalDateTime.now());
            contadors.add(contador);
        }

        session.setAttribute("contadors", contadors);

        article.getPropietats().forEach(prop -> {
            if (prop.getId() == propietats.getId()) {
                model.addAttribute("propietats", propietats);
                model.addAttribute("preuEuros", frontService.formatearComoEuros(prop.getPreu()));
                model.addAttribute("propietatsArticles", frontService.getPropietatsRepository().findAll());
                model.addAttribute("contadors", session.getAttribute("contadors"));

            }
        });

        model.addAttribute("imatges", frontService.getImatgeRepository().findAll());
        model.addAttribute("valoracions", frontService.getValoracionsPerArticle(idArticle));
        model.addAttribute("addvaloracio", new AddValorationForm());

        return "article";
    }


    @GetMapping("/api/pagament")
    public String pagament(HttpServletRequest request, @RequestParam String resp, @RequestParam String status, @RequestParam String paymentMethod) {
        HttpSession session = request.getSession();


        if (session == null) return "redirect:/error";

        // if (status.equals("COMPLETED") && paymentMethod.toLowerCase().equals("paypal")){
        Client client = (Client) session.getAttribute("persona");
        Client clientDB = frontService.getClientRepository().findById(client.getId()).orElse(null);
        if (clientDB == null) return "redirect:/error";
        ShoppingCart carrito = (ShoppingCart) session.getAttribute("carrito");

        Set<Cart> carts = carrito.getIds();

        for (Cart cart : carts) {
            Factura factura = new Factura();
            factura.setClient(clientDB);
            factura.setPrice(cart.getPrice());
            factura.setPaymentMethod(paymentMethod);
            factura.setPaymentStatus(status);

            LineasFactura lineasFactura = new LineasFactura();
            lineasFactura.setFactura(factura);
            lineasFactura.setNumeroFactura(factura.getId());
            lineasFactura.setPrice(cart.getPrice());
            lineasFactura.setNomArticle(cart.getPropietats().getArticle().getNom());
            
            facturaRepository.save(factura);
            lineaFacturaRepository.save(lineasFactura);
        }


        /*Factura factura = new Factura();
        factura.setClient(clientDB);
        factura.setPrice(carrito.getTotal());
        factura.setPaymentMethod(paymentMethod);
        factura.setPaymentStatus(status);

        LineasFactura lineasFactura = new LineasFactura();
        lineasFactura.setFactura(factura);
        lineasFactura.setNumeroFactura(factura.getId());
        lineasFactura.setPrice(carrito.getTotal());

        facturaRepository.save(factura);*/


        return "redirect:/carrito/finalitzat";
        //} else {
        //   return "redirect:/carrito/error";
        //}
    }

    @GetMapping("/carrito/finalitzat")
    public String finalitzat(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "redirect:/";

        Client client = (Client) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");
        model.addAttribute("user", client);

        return "finalitzat";
    }

    @SuppressWarnings("all")
    @PostMapping("/areaclients/addvaloracio/{idArticle}/{idClient}/{idPropietat}")
    public String addValoracio(@PathVariable int idArticle, @ModelAttribute AddValorationForm addvaloracio, @PathVariable int idClient, @PathVariable int idPropietat) {
        frontService.addValoracio(idClient, idArticle, addvaloracio);
        return "redirect:/article/" + idArticle + "/" + idPropietat;
    }

    @PostMapping("/areaclients/updatevaloracio/{idArticle}/{idClient}/{idPropietat}/{idValoracio}")
    public String updateValoracio(@PathVariable int idArticle, @ModelAttribute AddValorationForm addvaloracio, @PathVariable int idClient, @PathVariable int idPropietat, @PathVariable int idValoracio) {
        System.out.println(addvaloracio);
        frontService.updateValoracio(idValoracio, addvaloracio);
        return "redirect:/article/" + idArticle + "/" + idPropietat;
    }

    @SuppressWarnings("all")
    @GetMapping("/areaclients/deletevaloracio/{id}/{idArticle}/{idPropietat}")
    public String deleteValoracio(@PathVariable int id, @PathVariable int idArticle, @PathVariable int idPropietat) {
        frontService.deleteValoracio(id);
        return "redirect:/article/" + idArticle + "/" + idPropietat;
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
