package me.pceconomic.shop.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Persona;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.logging.Logger;

@Component("loginInterceptor")
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger log = Logger.getLogger(LoginInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI());
        HttpSession session = request.getSession(false);
        Persona persona = (Persona) session.getAttribute("persona");
        if (persona == null) {
            log.info("Unauthorized access request");
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
