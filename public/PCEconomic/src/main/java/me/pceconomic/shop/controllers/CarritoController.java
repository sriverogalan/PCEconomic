package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.Cart;
import me.pceconomic.shop.domain.ShoppingCart;
import me.pceconomic.shop.domain.article.propietats.Propietats;
import me.pceconomic.shop.repositories.PropietatsRepository;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;


@RestController
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
    public RedirectView addArticleToCart(Model model, @RequestParam int idprops, @RequestParam int quantitat) {

        Propietats props = propietatsRepository.findById(idprops).orElse(null);

        if (props == null) return new RedirectView("/carrito");

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
            if (c.getIdprops() == idprops) {
                cart = c;
                break;
            }
        }

        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + quantitat);
        } else {
            cart = new Cart();
            cart.setIdprops(idprops);
            cart.setQuantity(quantitat);
            ids.add(cart);
        }

        shoppingCart.setIds(ids);
        session.setAttribute("carrito", shoppingCart);
        return new RedirectView("/carrito");
    }

    @GetMapping("/api/carrito")
    public ShoppingCart carrito(Model model) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");
        model.addAttribute("carrito", shoppingCart);
        frontService.sendListsToView(model);
        return shoppingCart;
    }
}
