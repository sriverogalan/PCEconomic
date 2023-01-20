package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.Cart;
import me.pceconomic.shop.domain.ShoppingCart;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
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
        List<Cart> ids = shoppingCart.getIds();

        if (ids == null) {
            ids = new ArrayList<>();
        }

        Cart cart = new Cart();
        cart.setIdprops(idprops);
        cart.setQuantity(quantitat);
        ids.add(cart);

        for (Cart c : ids) {
            if (c.getIdprops() == idprops) {
                c.setQuantity(quantitat);
            }
        }

        shoppingCart.setIds(ids);
        session.setAttribute("carrito", shoppingCart);
        return "redirect:/carrito";
    }

    @GetMapping("/api/carrito")
    public ShoppingCart carrito(Model model) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");
        model.addAttribute("carrito", shoppingCart);
        frontService.sendListsToView(model);
        return shoppingCart;
    }
}
