package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class RestMapsController {
    @Value("${google.maps.key}")
    private String key;

    @PostMapping("/maps")
    public String getDistance(HttpServletRequest dest) throws Exception {
        String destination = dest.getParameter("destination");
        destination = destination.replaceAll(" ", "%20");
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Manacor&destinations=" + destination + "&units=imperial&key=" + this.key;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        // return json response
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
