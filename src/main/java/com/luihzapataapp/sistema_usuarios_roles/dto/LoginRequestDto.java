// en este dto obtendremos la informacion que ingresa el usuario para inicia sesion
// esto lo hacemos necesariaente para respetar la arquitectura en capas y evitar el mal uso de  un dto y un usuario pueda modificar cosas de mas 
// aunque los dto al fin de cuentas se refieren al mismo correo y contraseña es mejor tenerlos seprados y no pasar campos demas para realizar una tarea
package com.luihzapataapp.sistema_usuarios_roles.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
    private String correo;
    private String contrasena;
}