package com.example.demo;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList; // Importante para inicializar la lista

/**
 * Entidad que representa a un usuario registrado en SpringChat.
 * Almacena sus credenciales, correo y la relación con los canales que ha creado.
 * * @author Alejandro
 * @version 1.0
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private String contrasena;
    
    private String email;

    // Un usuario tiene muchos mensajes
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes = new ArrayList<>(); // Inicializamos la lista vacía para evitar errores
    
    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL)
    private List<Canal> canalesCreados = new ArrayList<>();

    public List<Canal> getCanalesCreados() {
		return canalesCreados;
	}

	public void setCanalesCreados(List<Canal> canalesCreados) {
		this.canalesCreados = canalesCreados;
	}

	public Usuario() {
    }

	//	le he metido el atributo correo
    public Usuario(String nombre, String contrasena, String email) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}