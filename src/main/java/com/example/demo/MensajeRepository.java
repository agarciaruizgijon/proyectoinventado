package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repositorio para la gestión de mensajes.
 * Permite buscar todos los mensajes asociados a la ID de un canal específico.
 * * @author Alejandro
 */
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
	List<Mensaje> findByCanalId(Long canalId);
}