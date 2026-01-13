package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	// Buscar si existe el nombre (para el registro)
	Usuario findByNombre(String nombre);

	// Buscar por nombre Y contraseña (para el login)
	Usuario findByNombreAndContrasena(String nombre, String contrasena);
}