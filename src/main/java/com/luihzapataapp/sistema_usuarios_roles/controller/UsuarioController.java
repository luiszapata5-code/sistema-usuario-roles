package com.luihzapataapp.sistema_usuarios_roles.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; // al momento de hacer la verificaciones por medio del jwt ya no neceitamos los requestparam para obtener el id del usuario, ya que lo obtenemos del token

import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioBienvenidaDto;
import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioEditarDto;
import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioIngresoDto;
import com.luihzapataapp.sistema_usuarios_roles.services.UsuarioService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/me")



public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/perfil")
    public UsuarioIngresoDto mostrarUsuario(Authentication auth){
        Integer id=(Integer) auth.getPrincipal();
        UsuarioIngresoDto usuario = usuarioService.resumenUsuario(id);
        return usuario; 
    
    }
    @GetMapping("/bienvenida")
    public UsuarioBienvenidaDto mostrarBienvenida(Authentication auth){
        Integer id=(Integer) auth.getPrincipal();
        UsuarioBienvenidaDto bienvenida = usuarioService.bienvenida(id);
        return bienvenida; 
    }
    @PutMapping("/editar")
    public UsuarioIngresoDto editarUsuario(Authentication auth, @RequestBody UsuarioEditarDto datos){
        Integer id=(Integer) auth.getPrincipal();
        UsuarioIngresoDto usuario = usuarioService.editar(id, datos);
        return usuario;
    }

    @DeleteMapping("/eliminar")
    public String eliminarUsuario(Authentication auth){
        Integer id=(Integer) auth.getPrincipal();
        usuarioService.eliminarUsuario(id, id);
        return "Usuario eliminado exitosamente";
    }
}
