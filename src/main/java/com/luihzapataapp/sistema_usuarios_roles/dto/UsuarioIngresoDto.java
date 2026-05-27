package com.luihzapataapp.sistema_usuarios_roles.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioIngresoDto {
    private String nombre;
    private String correo;
    private String rol;
    private boolean estado;
    private OffsetDateTime fechaRegistro;
    private OffsetDateTime ultimoAcceso;
}
