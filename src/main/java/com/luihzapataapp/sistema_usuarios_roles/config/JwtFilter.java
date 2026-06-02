package com.luihzapataapp.sistema_usuarios_roles.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.luihzapataapp.sistema_usuarios_roles.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Leer el header Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Si no hay token o no empieza con "Bearer ", dejamos pasar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token quitando "Bearer "
        String token = authHeader.substring(7);

        // 4. Validar el token
        if (!jwtService.validarToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. Extraer ID y rol del token
        Integer idUsuario = jwtService.extraerIdUsuario(token);
        String rol = jwtService.extraerRol(token);

        // 6. Registrar al usuario en el contexto de seguridad
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                idUsuario,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase()))
            );

        SecurityContextHolder.getContext().setAuthentication(auth);

        // 7. Dejar pasar la petición
        filterChain.doFilter(request, response);
    }
}