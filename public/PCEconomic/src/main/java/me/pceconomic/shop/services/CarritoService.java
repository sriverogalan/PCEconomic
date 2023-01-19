package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    private final HttpSession session;

    @Autowired
    public CarritoService(HttpSession session) {
        this.session = session;
    }


}
