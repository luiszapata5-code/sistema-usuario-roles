package com.luihzapataapp.sistema_usuarios_roles.model;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name="usuario")
@Entity

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Integer idUsuario;



    @JoinColumn(
        name = "rol",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_clientes")
    )
    private Rol rol;

    @Column(name="nombre",length=150, nullable=false)
    private String nombre;
    @Column(name="apellido",length=150, nullable=false)
    private String apellido;
    @Column(name="correo",length=150, nullable=false, unique=true)
    private String correo;
    @Column(name="password hash",length=150, nullable=false)
    private String passwordHash;    
    @Column (name="estado", nullable=false)
    private boolean estado;
    @Column (name="fecha_registro", nullable=false)
    private LocalDate fechaRegistro;
    @Column(name="ultimo_acceso", nullable=false)
    private LocalDate ultimoAcceso;

        
}
