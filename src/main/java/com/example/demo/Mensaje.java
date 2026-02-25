package com.example.demo;

import jakarta.persistence.*;
/**
 * Entidad que representa un mensaje de texto enviado dentro de un canal.
 * Almacena el contenido, la fecha de envío, el autor y la sala a la que pertenece.
 * * @author TuNombre
 */
@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;
    private String fecha;

    // Muchos mensajes pertenecen a Un usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Esto crea la Foreign Key en MySQL
    private Usuario usuario;
    
 // Un mensaje ahora pertenece a un canal específico
    @ManyToOne
    @JoinColumn(name = "canal_id")
    private Canal canal;

    // Acuérdate de generar: public Canal getCanal() y public void setCanal(Canal canal)

    // Constructor vacío (obligatorio para JPA)
    public Mensaje() {
    }

    public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	// Constructor lleno (útil para crear mensajes rápido)
    public Mensaje(String texto, String fecha, Usuario usuario) {
        this.texto = texto;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    // --- GETTERS Y SETTERS MANUALES ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}