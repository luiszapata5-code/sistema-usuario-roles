package com.luihzapataapp.sistema_usuarios_roles.model;


// aqui tenemos los importts para decirle a java donde puede encontrar la herramientas
import jakarta.persistence.Column; // mapea las columnas de la base de datos
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;// lombok es una libreria que sirve para simplificar el codigo y le dice a java que debe generar segun lo pedido
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter// evita que los escribamos manualmente, lombok se encarga de eso por nosotros

@NoArgsConstructor// le dice a lombok que genere un constructor sin argumentos, es decir un constructor vacio
@Data
@Table(name = "roles")//especifica el nombre de la tabla de la base de datos sobre la que vamos a trabajar
@Entity // le indica a java que esta clase es una entidad de la base de datos, es decir que se va a mapear a una tabla de la base de datos
public class Rol {
    @Id// le indica a java que este atributo es la clave primaria de la tabla de la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)// el valor del id rol sera generado automaticamente por la base de datos. autoincrementado
    @Column(name="id_rol")// creamos la columna id_rol 
    private Integer idRol;// le asignamos una variable de tipo entero para que se guarde el id del rol, esta variable se va a mapear a la columna id_rol de la tabla roles de la base de datos
    
    @Column(name="tipo",length=150)// hacemos lo mismo con el tipo de rol, creamos la columna tipo y le asignamos una variable de tipo string para que se guarde el tipo de rol, esta variable se va a mapear a la columna tipo de la tabla roles de la base de datos
    private String tipo;


    
}