package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.other.Carrito;
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

    public void addToCart(Propietats propietats) {
        List<Carrito> carritoList = this.getCarrito();
        if (carritoList == null) {
            carritoList = new ArrayList<>();
        }
        Carrito carrito = new Carrito();
        carrito.setId(carritoList.size() + 1);
        carrito.setCantidad(1);
        carrito.setPropietatsList(new ArrayList<>());
        carritoList.add(carrito);
        session.setAttribute("carrito", carritoList);
    }

    public List<Carrito> getCarrito() {
        List<Carrito> carrito = (List<Carrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }
}
