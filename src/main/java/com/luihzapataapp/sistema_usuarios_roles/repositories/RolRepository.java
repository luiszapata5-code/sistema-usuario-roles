package com.luihzapataapp.sistema_usuarios_roles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luihzapataapp.sistema_usuarios_roles.model.Rol;
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
}
