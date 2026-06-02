package com.luihzapataapp.sistema_usuarios_roles.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioEditarDto;
import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioIngresoDto;
import com.luihzapataapp.sistema_usuarios_roles.model.Usuario;
import com.luihzapataapp.sistema_usuarios_roles.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor

public class AdminController {
    private final UsuarioService usuarioService;

    // GET /api/admin/usuarios → lista todos
    @GetMapping("/usuarios")
    public List<UsuarioIngresoDto> listar() {
        return usuarioService.listarUsuarios();
    }

    // POST /api/admin/usuarios → crear con rol específico
    // recibe Usuario en body + rolNombre como RequestParam
    @PostMapping("/usuarios")
    public UsuarioIngresoDto crear(@RequestBody Usuario usuario, @RequestParam String rolNombre) {
        return usuarioService.crearUsuarioAdmin(usuario, rolNombre);
    }

    // PUT /api/admin/usuarios/{id} → editar cualquier usuario
    // {id} en la URL → @PathVariable
    @PutMapping("/usuarios/{id}")
    public UsuarioIngresoDto editar(@PathVariable Integer id,@RequestBody UsuarioEditarDto datos) {
        return usuarioService.editar(id, datos);
    }

    // DELETE /api/admin/usuarios/{id} → eliminar
    // recibe también el id del admin para verificar que no se elimine a sí mismo
    @DeleteMapping("/usuarios/{id}")
    public String eliminar(@PathVariable Integer id,@RequestParam Integer idAdmin) {
        usuarioService.eliminarUsuario(id, idAdmin);
        return "Usuario eliminado";
    }
}

