package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class RestMapsController {
    @Value("${google.maps.key}")
    private String key;

    @GetMapping("/maps")
    public String postDistance(HttpServletRequest destination) throws IOException {
        String dest = destination.getParameter("destination");
        return getDistance(dest.replaceAll(" ", "%20"));
    }

    private String getDistance(String dest) throws IOException {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Manacor&destinations=" + dest + "&units=imperial&key=" + this.key;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

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
