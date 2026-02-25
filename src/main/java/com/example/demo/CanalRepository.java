package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio para la gestión de canales.
 * Incluye consultas personalizadas para contar los canales de un usuario y buscar por nombre.
 * * @author Alejandro
 */
public interface CanalRepository extends JpaRepository<Canal, Long> {
    
    long countByCreador(Usuario creador);
    
    Canal findByNombre(String nombre);
}