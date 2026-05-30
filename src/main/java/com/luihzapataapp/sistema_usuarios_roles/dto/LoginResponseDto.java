package com.luihzapataapp.sistema_usuarios_roles.dto;
// este dto se encargara de devolver la informacion que necesitara el usuario para iniciar sesion y navegar en la pagina

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginResponseDto{
    private String token; // este es el jwt que se le devolvera al usuario con un tiempo de expiracion para que pueda navegar en la pagina sin necesidad de volver a iniciar sesion cada vez que quiera acceder a una nueva pagina
    private String nombre;
    private String rol;
}

