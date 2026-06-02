package com.luihzapataapp.sistema_usuarios_roles.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luihzapataapp.sistema_usuarios_roles.dto.LoginRequestDto;
import com.luihzapataapp.sistema_usuarios_roles.dto.LoginResponseDto;
import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioIngresoDto;
import com.luihzapataapp.sistema_usuarios_roles.model.Usuario;
import com.luihzapataapp.sistema_usuarios_roles.services.AuthService;
import com.luihzapataapp.sistema_usuarios_roles.services.UsuarioService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController 



public class AuthController {
    private final AuthService authService;
    private final UsuarioService usuarioService;
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        
        LoginResponseDto response = authService.login(loginRequestDto);
        return response;
    }

  
    @PostMapping("/register")
    public UsuarioIngresoDto register(@RequestBody Usuario nuevousuario){
        UsuarioIngresoDto registro = usuarioService.registrarUsuario(nuevousuario);
        return registro;
    }
 
    
    
}
