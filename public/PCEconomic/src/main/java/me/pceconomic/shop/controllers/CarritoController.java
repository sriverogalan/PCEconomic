package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.carrito.Cart;
import me.pceconomic.shop.domain.carrito.ShoppingCart;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.repositories.PropietatsRepository;
import me.pceconomic.shop.services.CarritoService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CarritoController {

    private final HttpSession session;
    private final FrontService frontService;
    private final PropietatsRepository propietatsRepository;

    @Autowired
    public CarritoController(CarritoService carritoService, HttpSession session, FrontService frontService, PropietatsRepository propietatsRepository) {
        this.frontService = frontService;
        this.propietatsRepository = propietatsRepository;
        this.session = carritoService.getSession();
    }

    @GetMapping("/addcarrito")
    public String addArticleToCart(@RequestParam int idprops, @RequestParam int quantitat, @RequestParam(required = false) boolean isMain) {
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

        double price = props.getPreu() * quantitat;
        ;

        if (cart != null) {
            if (cart.getQuantity() >= props.getStock()) {
                cart.setQuantity(props.getStock());
                cart.setPrice(cart.getPrice());
            } else {
                cart.setQuantity(cart.getQuantity() + quantitat);
                cart.setPrice(cart.getPrice() + price);
            }
        } else {
            cart = new Cart();
            cart.setPropietats(props);
            cart.setQuantity(quantitat);
            cart.setPrice(price);
            ids.add(cart);
        }

        shoppingCart.setIds(ids);

        double total = 0;
        for (Cart c : ids) {
            total += c.getPrice();
        }

        shoppingCart.setTotal(total);
        session.setAttribute("carrito", shoppingCart);
        return isMain ? "redirect:/" : "redirect:/carrito";
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

        double price = props.getPreu() * quantitat;

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
        double total = 0;
        for (Cart c : ids) {
            total += c.getPrice();
        }

        shoppingCart.setTotal(total);
        session.setAttribute("carrito", shoppingCart);
        return "redirect:/carrito";
    }

    @GetMapping("/deletecarrito")
    public String deleteArticleToCart(@RequestParam int idprops) {
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

        if (cart != null) {
            ids.remove(cart);
        }
        session.setAttribute("carrito", shoppingCart);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String carrito(Model model) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");
        model.addAttribute("carrito", Objects.requireNonNullElseGet(shoppingCart, ShoppingCart::new));
        frontService.sendListsToView(model);
        return "carrito";
    }
}
