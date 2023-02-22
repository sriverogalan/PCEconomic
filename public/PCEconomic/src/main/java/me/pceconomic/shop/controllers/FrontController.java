package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.ContadorArticle;
import me.pceconomic.shop.domain.carrito.Cart;
import me.pceconomic.shop.domain.carrito.ShoppingCart;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.article.factura.Factura;
import me.pceconomic.shop.domain.entities.article.factura.LineasFactura;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.AddValorationForm;
import me.pceconomic.shop.domain.forms.areaclients.AddDirectionForm;
import me.pceconomic.shop.repositories.FacturaRepository;
import me.pceconomic.shop.repositories.LineaFacturaRepository;
import me.pceconomic.shop.repositories.ValoracionsRepository;
import me.pceconomic.shop.repositories.VisitaRepository;
import me.pceconomic.shop.services.CarritoService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class FrontController {

    private final FrontService frontService;
    private final CarritoService carritoService;
    private final VisitaRepository visitaRepository;
    private final FacturaRepository facturaRepository;
    private final LineaFacturaRepository lineaFacturaRepository;
    private final ValoracionsRepository valoracionsRepository;

    @Autowired
    public FrontController(FrontService frontService, LineaFacturaRepository lineaFacturaRepository, CarritoService carritoService, VisitaRepository visitaRepository, FacturaRepository facturaRepository, ValoracionsRepository valoracionsRepository) {
        this.frontService = frontService;
        this.carritoService = carritoService;
        this.visitaRepository = visitaRepository;
        this.facturaRepository = facturaRepository;
        this.lineaFacturaRepository = lineaFacturaRepository;
        this.valoracionsRepository = valoracionsRepository;
    }

    @GetMapping("/lang")
    public String langGenerator( ) {
        return "/generarLang/lang";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        if (session == null) return "index";


        Persona persona = (Persona) session.getAttribute("persona");
        model.addAttribute("client", persona == null ? "LOGIN" : "LOGOUT");
        model.addAttribute("user", persona);
        model.addAttribute("rols", persona == null ? null : persona.getRols());
        model.addAttribute("token", session.getAttribute("token"));

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
                model.addAttribute("categoria", article.getSubcategories());
            }
        });


        model.addAttribute("imatges", frontService.getImatgeRepository().findAll());
        model.addAttribute("valoracions", frontService.getValoracionsPerArticle(article));
        model.addAttribute("addvaloracio", new AddValorationForm());

        return "article";
    }



    @PostMapping("/pagament")
    public String pagament(HttpServletRequest request, @RequestParam String status, @RequestParam String paymentMethod) {
        HttpSession session = request.getSession();
        if (session == null) return "redirect:/";

        System.out.println(status);
        System.out.println(paymentMethod);

        if (status.equals("COMPLETED") && paymentMethod.toLowerCase().equals("paypal")) {
            Persona persona = (Persona) session.getAttribute("persona");
            ShoppingCart carrito = (ShoppingCart) session.getAttribute("carrito");
            System.out.println(" ----------------------- ");
            System.out.println(persona);
            System.out.println(carrito);
            System.out.println(" ----------------------- ");
            Set<Cart> carts = carrito.getIds();
            System.out.println(carts);
            System.out.println();

            if (!frontService.getPersonaRepository().existsById(persona.getId())) {
                return "redirect:/error";
            }

            Factura factura = new Factura();
            factura.setClient(persona);
            factura.setPreu(carrito.getPreuTotal());
            factura.setMetodePagament(paymentMethod);
            factura.setEstat(status);
            factura.setData(LocalDate.now());
            factura.setQuantitat(carts.size());
            factura.setPreuTransport(carrito.getPreuTransport());
            factura.setDireccio(carrito.getDireccio());

            Set<LineasFactura> facturaSet = new HashSet<>();
            for (Cart cart : carts) {
                LineasFactura lineasFactura = new LineasFactura();
                lineasFactura.setFactura(factura);
                lineasFactura.setNomArticle(cart.getPropietats().getArticle().getNom());
                lineasFactura.setPropietats(cart.getPropietats());
                lineasFactura.setPrice(cart.getPrice());
                lineasFactura.setQuantity(cart.getQuantity());
                lineasFactura.setMarca(cart.getPropietats().getArticle().getMarca());

                facturaSet.add(lineasFactura);

                Propietats propietats = frontService.getPropietatsRepository().findById(cart.getPropietats().getId()).orElse(null);
                if (propietats == null) return "redirect:/error";
                propietats.setStock(propietats.getStock() - cart.getQuantity());
                frontService.getPropietatsRepository().save(propietats);
            }
            factura.setLineasFacturas(facturaSet);
            persona.getFactures().add(factura);
            frontService.getPersonaRepository().save(persona);

            session.removeAttribute("carrito");
            session.removeAttribute("persona");
            session.removeAttribute("pedidos");
            session.setAttribute("pedidos", persona.getFactures());
            session.setAttribute("persona", persona);

            return "redirect:/carrito/finalitzat";
        }
        return "redirect:/carrito/error";
    }

    @GetMapping("/carrito/finalitzat")
    public String finalitzat(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "pagorealizado";

        Persona persona = (Persona) session.getAttribute("persona");
        model.addAttribute("client", persona == null ? "LOGIN" : "LOGOUT");
        model.addAttribute("user", persona);

        return "finalitzat";
    }

    @PostMapping("/areaclients/addvaloracio/{idArticle}/{idPropietat}/{idLinea}")
    public String addValoracio(HttpServletRequest request, @PathVariable int idLinea, @PathVariable int idArticle, @ModelAttribute AddValorationForm addvaloracio, @PathVariable int idPropietat) {
        HttpSession session = request.getSession();
        if (session == null) return "redirect:/";
        frontService.addValoracio(session, idArticle, addvaloracio, idPropietat, idLinea);
        return "redirect:/article/" + idArticle + "/" + idPropietat;
    }

    @PostMapping("/areaclients/updatevaloracio/{idArticle}/{idClient}/{idPropietat}/{idValoracio}")
    public String updateValoracio(@PathVariable int idArticle, @ModelAttribute AddValorationForm addvaloracio, @PathVariable int idClient, @PathVariable int idPropietat, @PathVariable int idValoracio) {
        System.out.println(addvaloracio);
        Valoracions valoracions = null;

        for (Valoracions valoracions1 : frontService.getValoracionsRepository().findAll()) {
            if (valoracions1.getId() == idValoracio) {
                valoracions = valoracions1;
                break;
            }
        }

        if (valoracions == null) return "redirect:/error";
        if (addvaloracio.getValoracio() == 0.0 && addvaloracio.getComentari() == null) return "redirect:/error";
        if (addvaloracio.getValoracio() == 0.0) return "redirect:/error";
        if (addvaloracio.getComentari() == null) return "redirect:/error";
        if (addvaloracio.getComentari().equals("")) return "redirect:/error";

        valoracions.setValoracio(addvaloracio.getValoracio());
        valoracions.setComentari(addvaloracio.getComentari());
        valoracions.setData(LocalDate.now());

        valoracionsRepository.save(valoracions);
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

        Persona client = (Persona) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");
        return false;
    }

}
