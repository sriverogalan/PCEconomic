package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.Cart;
import me.pceconomic.shop.domain.ShoppingCart;
import me.pceconomic.shop.domain.article.propietats.Propietats;
import me.pceconomic.shop.repositories.PropietatsRepository;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("carrito")
public class CarritoController {

    private final HttpSession session;
    private final FrontService frontService;
    private final PropietatsRepository propietatsRepository;

    @Autowired
    public CarritoController(HttpSession session, FrontService frontService, PropietatsRepository propietatsRepository) {
        this.session = session;
        this.frontService = frontService;
        this.propietatsRepository = propietatsRepository;
    }

    @GetMapping("/addcarrito")
    public String addArticleToCart(@RequestParam int idprops, @RequestParam int quantitat) {
        Propietats props = propietatsRepository.findById(idprops).orElse(null);

        if (props == null) return "/carrito";

        if (quantitat > props.getStock()) {
            quantitat = props.getStock();
        }

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        List<Cart> ids = shoppingCart.getIds();

        if (ids == null) {
            ids = new ArrayList<>();
        }

        Cart cart = null;
        for (Cart c : ids) {
            if (c.getPropietats().getId() == idprops) {
                cart = c;
                break;
            }
        }

        double price = props.getPreu() * quantitat;;

        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + quantitat);
            cart.setPrice(cart.getPrice() + price);
        } else {
            cart = new Cart();
            cart.setPropietats(props);
            cart.setQuantity(quantitat);
            cart.setPrice(price);
            ids.add(cart);
        }

        shoppingCart.setIds(ids);
        session.setAttribute("carrito", shoppingCart);
        return "redirect:/carrito";
    }

    @GetMapping("/updatecarrito")
    public String updateArticleToCart(@RequestParam int idprops, @RequestParam int quantitat) {
        Propietats props = propietatsRepository.findById(idprops).orElse(null);

        if (props == null) return "/carrito";

        if (quantitat > props.getStock()) {
            quantitat = props.getStock();
        }

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        List<Cart> ids = shoppingCart.getIds();

        if (ids == null) {
            ids = new ArrayList<>();
        }

        Cart cart = null;
        for (Cart c : ids) {
            if (c.getPropietats().getId() == idprops) {
                cart = c;
                break;
            }
        }

        double price = props.getPreu() * quantitat;;

        if (cart != null) {
            cart.setQuantity(quantitat);
            cart.setPrice(price);
        } else {
            cart = new Cart();
            cart.setPropietats(props);
            cart.setQuantity(quantitat);
            cart.setPrice(price);
            ids.add(cart);
        }

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
