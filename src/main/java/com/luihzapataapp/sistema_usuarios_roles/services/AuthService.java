package com.luihzapataapp.sistema_usuarios_roles.services;


import java.time.OffsetDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luihzapataapp.sistema_usuarios_roles.dto.LoginRequestDto;
import com.luihzapataapp.sistema_usuarios_roles.dto.LoginResponseDto;
import com.luihzapataapp.sistema_usuarios_roles.model.Usuario;
import com.luihzapataapp.sistema_usuarios_roles.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service


public class AuthService {
    private  final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

   public LoginResponseDto login(LoginRequestDto loginRequest) {

    // 1. Primero buscar el usuario
    Usuario usuario = usuarioRepository.findByCorreo(loginRequest.getCorreo())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // 2. Luego verificar contraseña
    if (!passwordEncoder.matches(loginRequest.getContrasena(), usuario.getPasswordHash())) {
        throw new RuntimeException("Usuario o contraseña incorrectos");
    }

    // 3. Luego verificar estado
    if (!usuario.isEstado()) {
        throw new RuntimeException("Usuario inactivo");
    }

    // 4. Actualizar ultimo acceso
    usuario.setUltimoAcceso(OffsetDateTime.now());
    usuarioRepository.save(usuario);

    // 5. Generar token
    String token = jwtService.generarToken(usuario.getIdUsuario(), usuario.getRol().getTipo());

    // 6. Devolver respuesta
    return new LoginResponseDto(
        token,
        usuario.getNombre(),
        usuario.getRol().getTipo(),
        usuario.getIdUsuario()
    );
}
    
}
