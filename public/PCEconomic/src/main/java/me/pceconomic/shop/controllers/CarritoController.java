package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
public class CarritoController {

    private final HttpSession session;
    private final FrontService frontService;
    private final PropietatsRepository propietatsRepository;
    private final CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService, FrontService frontService, PropietatsRepository propietatsRepository) {
        this.frontService = frontService;
        this.propietatsRepository = propietatsRepository;
        this.carritoService = carritoService;
        this.session = carritoService.getSession();
    }

    @GetMapping("/addcarrito")
    public String addArticleToCart(@RequestParam int idprops, @RequestParam int quantitat, @RequestParam(required = false) boolean isMain) {
        Propietats props = propietatsRepository.findById(idprops).orElse(null);
        if (props == null) return "/carrito";

        int cant = quantitat > props.getStock() ? props.getStock() : quantitat;
        ShoppingCart shoppingCart = Objects.requireNonNullElseGet((ShoppingCart) session.getAttribute("carrito"), ShoppingCart::new);

        Set<Cart> ids = shoppingCart.getIds() == null ? new HashSet<>() : shoppingCart.getIds();

        Cart cart = ids.stream().filter(c -> c.getPropietats().getId() == idprops).findFirst().orElse(null);
        double price = props.getPreu() * cant;

        if (cart != null) {
            if (cart.getQuantity() >= props.getStock()) {
                cart.setQuantity(props.getStock());
                cart.setPrice(cart.getPrice());
            } else {
                cart.setQuantity(cart.getQuantity() + cant);
                cart.setPrice(cart.getPrice() + price);
            }
        } else {
            cart = new Cart();
            cart.setPropietats(props);
            cart.setQuantity(cant);
            cart.setPrice(price);
            ids.add(cart);
        }

        shoppingCart.setIds(ids);

        carritoService.setTotal(shoppingCart);
        session.setAttribute("carrito", shoppingCart);
        return isMain ? "redirect:/" : "redirect:/carrito";
    }

    @GetMapping("/updatecarrito")
    public String updateArticleToCart(@RequestParam int idprops, @RequestParam int quantitat) {
        Propietats props = propietatsRepository.findById(idprops).orElse(null);
        if (props == null) return "/carrito";

        int cant = quantitat > props.getStock() ? props.getStock() : quantitat;

        ShoppingCart shoppingCart = Objects.requireNonNullElseGet((ShoppingCart) session.getAttribute("carrito"), ShoppingCart::new);
        Set<Cart> ids = shoppingCart.getIds() == null ? new HashSet<>() : shoppingCart.getIds();

        Cart cart = ids.stream().filter(c -> c.getPropietats().getId() == idprops).findFirst().orElse(null);
        double price = props.getPreu() * cant;

        if (cart != null) {
            cart.setQuantity(cant);
            cart.setPrice(price);
        } else {
            cart = new Cart();
            cart.setPropietats(props);
            cart.setQuantity(cant);
            cart.setPrice(price);
            ids.add(cart);
        }

        shoppingCart.setIds(ids);
        carritoService.setTotal(shoppingCart);

        session.setAttribute("carrito", shoppingCart);
        return "redirect:/carrito";
    }

    @GetMapping("/deletecarrito")
    public String deleteArticleToCart(@RequestParam int idprops) {
        ShoppingCart shoppingCart = Objects.requireNonNullElseGet((ShoppingCart) session.getAttribute("carrito"), ShoppingCart::new);
        Set<Cart> ids = shoppingCart.getIds() == null ? new HashSet<>() : shoppingCart.getIds();
        ids.stream().filter(c -> c.getPropietats().getId() == idprops).findFirst().ifPresent(ids::remove);
        carritoService.setTotal(shoppingCart);

        session.setAttribute("carrito", shoppingCart);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String carrito(Model model, HttpServletRequest request) {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");
        model.addAttribute("carrito", Objects.requireNonNullElseGet(shoppingCart, ShoppingCart::new));
        frontService.sendListsToView(model, request);
        return "carrito";
    }
}
