package me.pceconomic.shop.services;

import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public @Data class CarritoService {

    @Autowired
    private HttpSession session;

}
