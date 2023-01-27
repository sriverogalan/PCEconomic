package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class MapsController {
    @Value("${google.maps.key}")
    private String key;
    @GetMapping("/maps")
    public String getDistance(HttpServletRequest request) throws Exception {
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Manacor&destinations=Inca,%20Mallorca,%20Spain&units=imperial&key=" + this.key;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        // devuelve un json
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
