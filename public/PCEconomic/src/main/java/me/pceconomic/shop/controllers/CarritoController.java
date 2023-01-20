package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.Ids;
import me.pceconomic.shop.domain.ShoppingCart;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes("carrito")
public class CarritoController {

    private final HttpSession session;
    private final FrontService frontService;

    @Autowired
    public CarritoController(HttpSession session, FrontService frontService) {
        this.session = session;
        this.frontService = frontService;
    }

    @GetMapping("/addcarrito")
    public String addArticleToCart(Model model, @RequestParam int idprops, @RequestParam int quantitat) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }

        List<Ids> ids = shoppingCart.getIds();

        if (ids == null) {
            ids = new ArrayList<>();
        }

        Ids id = new Ids();
        id.setIdprops(idprops);
        id.setQuantity(quantitat);
        ids.add(id);

        shoppingCart.setIds(ids);
        session.setAttribute("carrito", shoppingCart);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String carrito(Model model) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");
        model.addAttribute("carrito", shoppingCart);
        frontService.sendListsToView(model);
        return "carrito";
    }
}
