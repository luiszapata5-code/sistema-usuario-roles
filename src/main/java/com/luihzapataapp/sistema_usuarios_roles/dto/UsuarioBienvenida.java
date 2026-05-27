package com.luihzapataapp.sistema_usuarios_roles.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioBienvenida {
    private String nombre;
    private String rol;

}