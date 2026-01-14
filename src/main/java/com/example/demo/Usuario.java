package com.example.demo;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList; // Importante para inicializar la lista

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // En MySQL esto impedirá que dos usuarios se llamen igual
    private String nombre;

    private String contrasena;

    // Un usuario tiene muchos mensajes
    // 'mappedBy' indica que la dueña de la relación es la variable "usuario" en la clase Mensaje
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes = new ArrayList<>(); // Inicializamos la lista vacía para evitar errores

    public Usuario() {
    }

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    // --- GETTERS Y SETTERS ---

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

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}