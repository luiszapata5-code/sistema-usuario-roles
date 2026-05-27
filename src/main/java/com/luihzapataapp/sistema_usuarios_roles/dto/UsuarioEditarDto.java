package com.luihzapataapp.sistema_usuarios_roles.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEditarDto {
    private String nombre;
    private String apellido;  
    private String correo;
    private String nuevaContrasena; // opcional
}
