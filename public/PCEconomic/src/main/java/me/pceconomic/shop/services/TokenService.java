package me.pceconomic.shop.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String createToken1Hour(String email, List<String> rols) {
        return Jwts.builder()
                .claim("email", email)
                .claim("rols", rols)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(this.jwtSecret.getBytes()))
                .compact();
    }

    public String createToken10Minute(String email, List<String> rols) {
        return Jwts.builder()
                .claim("email", email)
                .claim("rols", rols)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000)) // 10 minutes
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(this.jwtSecret.getBytes()))
                .compact();
    }

    public int validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(this.jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token).getBody();
            return 0;
        } catch (ExpiredJwtException e) {
            return 1;
        } catch (Exception e) {
            return 2;
        }
    }

    public Claims getClaims(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = auth.replace("Bearer ", "");
        return getClaims(token);
    }

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(this.jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
}
