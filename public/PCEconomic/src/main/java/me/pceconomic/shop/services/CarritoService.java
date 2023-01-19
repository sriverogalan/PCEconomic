package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {

    private final HttpSession session;

    @Autowired
    public CarritoService(HttpSession session) {
        this.session = session;
    }

    public List<Propietats> getCarrito() {
        List<Propietats> carrito = (List<Propietats>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }
}
