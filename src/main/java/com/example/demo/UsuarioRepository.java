package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio para la gestión de usuarios en la base de datos.
 * Proporciona métodos automáticos de Spring Data JPA.
 * * @author Alejandro
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	// Buscar si existe el nombre (para el registro)
	Usuario findByNombre(String nombre);

	// Buscar por nombre Y contraseña (para el login)
	Usuario findByNombreAndContrasena(String nombre, String contrasena);
}