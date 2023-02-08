package me.pceconomic.shop.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.carrito.ShoppingCart;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CompraInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrito");

        if (shoppingCart == null || shoppingCart.getIds() == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }

        if (shoppingCart.getIds().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }

        return true;
    }
}
