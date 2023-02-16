package me.pceconomic.shop.interceptors;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.repositories.PersonaRepository;
import me.pceconomic.shop.repositories.RolsRepository;
import me.pceconomic.shop.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private final PersonaRepository personaRepository;
    private final RolsRepository rolsRepository;

    @Autowired
    public TokenInterceptor(TokenService tokenService, PersonaRepository personaRepository, RolsRepository rolsRepository) {
        this.tokenService = tokenService;
        this.personaRepository = personaRepository;
        this.rolsRepository = rolsRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        System.out.println("Authorization: " + authorization);
        if (authorization != null && !authorization.isEmpty()) {
            String token = authorization.replace("Bearer ", "");
            int validate = tokenService.validateToken(token);

            if (validate == 2) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No valid Token");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            } else if (validate == 1) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired Token");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
            Claims claims = tokenService.getClaims(request);

            String email = claims.get("email", String.class);
            Persona persona = personaRepository.findByEmail(email);

            if (persona == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not valid");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }

            String URI = request.getRequestURI();
            String[] rolsURL = getRolsFromURL(URI);

            boolean hasAuthorization = this.hasAuthorization(persona, rolsURL);

            System.out.println("Auth: " + hasAuthorization);
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }

    private String[] getRolsFromURL(String url) {
        Multimap<String, String[]> securedURLs = LinkedHashMultimap.create();

        securedURLs.put("/areaclients", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/addDirection/", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/deleteDirection/", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/addDirection/buying", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/deletedirection/", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/changeName", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/changepassword", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/changeemail", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/changetelephone", new String[]{"CLIENT"});
        securedURLs.put("/carrito/finalitzat", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/addvaloracio/", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/updatevaloracio/", new String[]{"CLIENT"});
        securedURLs.put("/areaclients/deletevaloracio/", new String[]{"CLIENT"});
        securedURLs.put("/carrito/finalitzat", new String[]{"CLIENT"});
        securedURLs.put("/carrito/direccion", new String[]{"CLIENT"});
        securedURLs.put("/carrito/compra", new String[]{"CLIENT"});

        Map.Entry<String, String[]> securedURL =
                securedURLs.entries().stream()
                        .filter(urlsecured -> url.contains(urlsecured.getKey()))
                        .findFirst().orElse(null);

        if (securedURL != null) return securedURL.getValue();
        System.out.println("Error Secured URL: " + url);
        return null;
    }

    private boolean hasAuthorization(Persona persona, String... allowedRols) throws MessagingException, GeneralSecurityException, IOException {

        if (allowedRols == null) return false;
        if (persona == null) return false;

        for (String rol : allowedRols) {
            if (persona.getRols() != null) {
                if (persona.getRols().contains(rolsRepository.getRolsByName(rol))) {
                    return true;
                }
            }
        }
        return false;
    }
}
