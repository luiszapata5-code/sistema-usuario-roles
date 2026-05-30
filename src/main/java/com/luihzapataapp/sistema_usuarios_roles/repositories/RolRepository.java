package com.luihzapataapp.sistema_usuarios_roles.repositories;
// gestionan la comunicacion con la base de datos. 
// permiten realizar operaciones de CRUD (crear, leer, actualizar y eliminar) sobre las entidades de la base de datos. sin necesidad de escribir codigo SQL, gracias a las herramientas que nos brinda Spring Data JPA

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luihzapataapp.sistema_usuarios_roles.model.Rol;
// con esta anotacion le decimos a java que esta interfaz es un repositorio, es decir que se va a encargar de la comunicacion con la base de datos para la entidad Rol
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
}
