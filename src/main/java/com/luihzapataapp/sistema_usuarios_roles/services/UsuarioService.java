package com.luihzapataapp.sistema_usuarios_roles.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioBienvenidaDto;
import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioEditarDto;
import com.luihzapataapp.sistema_usuarios_roles.dto.UsuarioIngresoDto;
import com.luihzapataapp.sistema_usuarios_roles.model.Rol;
import com.luihzapataapp.sistema_usuarios_roles.model.Usuario;
import com.luihzapataapp.sistema_usuarios_roles.repositories.RolRepository;
import com.luihzapataapp.sistema_usuarios_roles.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepositorio;
    private final RolRepository rolRepositorio;
    private final PasswordEncoder passwordEncoder;
   

    public UsuarioIngresoDto registrarUsuario(Usuario nuevousuario){
    

        if(usuarioRepositorio.existsByCorreo(nuevousuario.getCorreo())){
            throw new RuntimeException("El usuario ya existe");
        }

        String contraseñaEcriptada=passwordEncoder.encode(nuevousuario.getPasswordHash());
        nuevousuario.setPasswordHash(contraseñaEcriptada);

        // para los usuarios se les asigna el rol de usuario por defecto
        Rol rolUsuario=rolRepositorio.findById(2).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        nuevousuario.setRol(rolUsuario);

        // le asignamos una fecha de regristro y de ingreso
        nuevousuario.setFechaRegistro(OffsetDateTime.now());
        nuevousuario.setUltimoAcceso(OffsetDateTime.now());

        // activamos su estado por defecto
        nuevousuario.setEstado(true);
        // Ahora si devolvemos el usuario con todos sus datos menos la contraseña por cuestion de buans practicas y de seguridad
        Usuario guardado=usuarioRepositorio.save(nuevousuario);//pero antes de retornar el usuariocon toda su informacion necesitamos que la base de datos genere el id del usuario y lo asigne al objeto guardado, para eso usamos el metodo save de jpa repository que se encarga de eso por nosotros

        return new UsuarioIngresoDto(
            guardado.getNombre(),
            guardado.getCorreo(),
            guardado.getRol().getTipo(),
            guardado.isEstado(),
            guardado.getFechaRegistro(),
            guardado.getUltimoAcceso()
        );  

       
      


    } 

    public boolean mostrarEstado(Integer idUsuario){
        
        Usuario usuarioestado = usuarioRepositorio.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioestado.isEstado();
    }

    public UsuarioBienvenidaDto bienvenida(Integer idUsuario){
        Usuario usuariobienvenida = usuarioRepositorio.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UsuarioBienvenidaDto(
            usuariobienvenida.getNombre(),
            usuariobienvenida.getRol().getTipo()
        );
    }

    public UsuarioIngresoDto resumenUsuario(Integer idUsuario) {
    Usuario usuario = usuarioRepositorio.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    return new UsuarioIngresoDto(
        usuario.getNombre(),
        usuario.getCorreo(),
        usuario.getRol().getTipo(),
        usuario.isEstado(),
        usuario.getFechaRegistro(),
        usuario.getUltimoAcceso()
    );
}
    public UsuarioIngresoDto editar(Integer idUsuario, UsuarioEditarDto datos) {
        Usuario usuarioeditar = usuarioRepositorio.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        // vamos a hacer unas condiciones para que el usuario edite solo los campos que desee
        // si el campo nombre no es nulo entonces se edita el dato

        if (datos.getNombre()!=null){
            usuarioeditar.setNombre(datos.getNombre());

        }

        if (datos.getApellido()!=null){
            usuarioeditar.setApellido(datos.getApellido());
            
        }

        if (datos.getCorreo()!=null){
            // antes de editar el correo debemos verificar que el nuevo correo no exista en la base de datos para evitar duplicados
            if (usuarioRepositorio.existsByCorreo(datos.getCorreo())){
                throw new RuntimeException("El correo ya existe");
            }
            usuarioeditar.setCorreo(datos.getCorreo());
            
        }

        if(datos.getNuevaContrasena()!=null&& !datos.getNuevaContrasena().isEmpty()){
            String contraseñaEcriptada=passwordEncoder.encode(datos.getNuevaContrasena());
            usuarioeditar.setPasswordHash(contraseñaEcriptada);
        }
        Usuario actualizado=usuarioRepositorio.save(usuarioeditar);

       return new UsuarioIngresoDto(
            actualizado.getNombre(),
            actualizado.getCorreo(),
            actualizado.getRol().getTipo(),
            actualizado.isEstado(),
            actualizado.getFechaRegistro(),
            actualizado.getUltimoAcceso()
        );



    }

    public void eliminarUsuario(Integer idUsuarioAEliminar, Integer idAdmin) {

    // Regla del negocio: admin no puede borrarse a sí mismo
    if (idUsuarioAEliminar.equals(idAdmin)) {
        throw new RuntimeException("No puedes eliminar tu propia cuenta");
    }

    Usuario usuario = usuarioRepositorio.findById(idUsuarioAEliminar)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    usuarioRepositorio.deleteById(idUsuarioAEliminar);
}


    //funciones especiales para el admin 

    public List<UsuarioIngresoDto> listarUsuarios() {
    return usuarioRepositorio.findAll()
        .stream()
        .map(u -> new UsuarioIngresoDto(
            u.getNombre(),
            u.getCorreo(),
            u.getRol().getTipo(),
            u.isEstado(),
            u.getFechaRegistro(),
            u.getUltimoAcceso()
        ))
        .collect(Collectors.toList());
}

    public UsuarioIngresoDto crearUsuarioAdmin(Usuario nuevoUsuario, String rolNombre) {
    if (usuarioRepositorio.existsByCorreo(nuevoUsuario.getCorreo())) {
        throw new RuntimeException("El correo ya existe");
    }
    String hash = passwordEncoder.encode(nuevoUsuario.getPasswordHash());
    nuevoUsuario.setPasswordHash(hash);

    Rol rol = rolRepositorio.findByTipo(rolNombre)
    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    nuevoUsuario.setRol(rol);

    nuevoUsuario.setFechaRegistro(OffsetDateTime.now());
    nuevoUsuario.setUltimoAcceso(OffsetDateTime.now());
    nuevoUsuario.setEstado(true);

    Usuario guardado = usuarioRepositorio.save(nuevoUsuario);

    return new UsuarioIngresoDto(
        guardado.getNombre(),
        guardado.getCorreo(),
        guardado.getRol().getTipo(),
        guardado.isEstado(),
        guardado.getFechaRegistro(),
        guardado.getUltimoAcceso()
    );
}
}



