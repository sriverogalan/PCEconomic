package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpSession;
import lombok.Data;
import me.pceconomic.shop.domain.carrito.Cart;
import me.pceconomic.shop.domain.carrito.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public @Data class CarritoService {

    @Autowired
    private HttpSession session;

    public void setTotal(ShoppingCart shoppingCart) {
        double total = 0;
        for (Cart c : shoppingCart.getIds()) {
            total += c.getPrice();
        }
        shoppingCart.setTotal(total);
    }

}
