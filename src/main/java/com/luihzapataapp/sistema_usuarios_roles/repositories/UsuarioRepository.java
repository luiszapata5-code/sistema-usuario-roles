package com.luihzapataapp.sistema_usuarios_roles.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luihzapataapp.sistema_usuarios_roles.model.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreo(String correo);// este es una consulta sql que verifica la existencia de un correo en la base de datos, esto es para evitar que se repitan los correos en la base de datos, ya que el correo es un dato unico para cada usuario
    // gracias al jpa no tenemos que escribir la consulta sql, jpa se encarga de eso por nosotros, solo tenemos que definir el metodo con el nombre correcto y jpa se encarga de generar la consulta sql por nosotros
    Optional<Usuario> findByCorreo(String correo);// este es una consulta sql que busca un usuario por su correo, esto es para el proceso de inicio de sesion, ya que el usuario ingresa su correo y contraseña para iniciar sesion, entonces necesitamos buscar el usuario por su correo para verificar su contraseña 
    
    
}
    