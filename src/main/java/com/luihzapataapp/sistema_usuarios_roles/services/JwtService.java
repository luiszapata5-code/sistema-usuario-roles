package com.luihzapataapp.sistema_usuarios_roles.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // Lee el valor de jwt.secret desde application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // Lee el valor de jwt.expiration desde application.properties (86400000 = 24hrs)
    @Value("${jwt.expiration}")
    private long expiration;

    // Convierte el String secretKey en una clave criptográfica real
    // Keys.hmacShaKeyFor necesita los bytes del string — getBytes() los obtiene
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // MÉTODO 1 — Genera el token JWT
    // Recibe el ID y rol del usuario para meterlos dentro del token
    public String generarToken(Integer idUsuario, String rol) {

        // Map para guardar datos extra dentro del token (claims personalizados)
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol); // agrega el rol al token

        return Jwts.builder()
            .setClaims(claims)                    // agrega los claims personalizados
            .setSubject(String.valueOf(idUsuario)) // el subject es el ID del usuario
            .setIssuedAt(new Date())               // fecha de creación = ahora
            .setExpiration(new Date(System.currentTimeMillis() + expiration)) // fecha expiración
            .signWith(getSigningKey(), SignatureAlgorithm.HS256) // firma con tu clave secreta
            .compact(); // construye y devuelve el token como String
    }

    // MÉTODO 2 — Extrae el ID del usuario desde el token
    public Integer extraerIdUsuario(String token) {
        // getSubject() devuelve el subject que pusimos en generarToken()
        String id = extraerClaims(token).getSubject();
        return Integer.valueOf(id); // convierte el String a Integer
    }

    // MÉTODO 3 — Extrae el rol desde el token
    public String extraerRol(String token) {
        // get("rol") obtiene el claim personalizado que pusimos
        return extraerClaims(token).get("rol", String.class);
    }

    // MÉTODO 4 — Valida que el token no esté expirado ni manipulado
    public boolean validarToken(String token) {
        try {
            // parseClaimsJws lanza excepción si el token es inválido o expirado
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true; // si no lanzó excepción, el token es válido
        } catch (Exception e) {
            return false; // cualquier error = token inválido
        }
    }

    // MÉTODO PRIVADO — Lee y devuelve todos los claims del token
    // Es privado porque solo lo usan los métodos de esta clase
    private Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey()) // usa tu clave secreta para verificar
            .build()
            .parseClaimsJws(token)          // parsea el token
            .getBody();                     // devuelve el contenido (claims)
    }
}