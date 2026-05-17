CREATE TABLE roles (
  id_rol INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  tipo VARCHAR(150) NOT NULL
);
CREATE TABLE usuario(
  id_usuario INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  rol INTEGER NOT NULL REFERENCES roles(id_rol),
  nombre VARCHAR(150) NOT NULL,
  apellido VARCHAR(150) NOT NULL,
  correo VARCHAR(200) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  estado BOOLEAN DEFAULT TRUE,
  fecha_registro TIMESTAMPTZ DEFAULT NOW (),
  ultimo_acceso  TIMESTAMPTZ DEFAULT NOW ()

);
INSERT INTO roles (tipo) VALUES('usuario');
INSERT INTO roles (tipo) VALUES('admin');
INSERT INTO usuario (rol,nombre,apellido,correo,password_hash) VALUES (1,'luis','zapata','luis.zapata5@udea.edu.co','$2a$12$pU6Lvn8LKWhPkR7RdvZxze9FnCs3UAUkNWNdrckhekMkxWRbFtSXi')