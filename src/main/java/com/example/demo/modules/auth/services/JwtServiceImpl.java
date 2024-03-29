package com.example.demo.modules.auth.services;

import com.example.demo.modules.auth.filters.JwtProperties;
import com.example.demo.modules.auth.services.impl.JwtService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties properties;
    public Date extractExpirations(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public Boolean hasClaim(String token, String claimName){
        Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) throws JwtException {
        JwtParser parser = Jwts.parser().setSigningKey(properties.getSecretKey());
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        return claimsJws.getBody();
    }
    public Boolean isTokenExpired(String token){
        return extractExpirations(token).before(new Date());
    }

    @Override
    public String extractUserName(String token) {
        return extractUsername(token);
    }

    public String generateToken(UserDetails userDetails){
        Map<String , Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS256, properties.getSecretKey()).compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

