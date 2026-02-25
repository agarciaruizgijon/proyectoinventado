package com.example.demo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Entidad que representa una sala de chat (Canal).
 * Contiene el nombre, la descripción, su creador y la lista de mensajes enviados en ella.
 * * @author TuNombre
 */
@Entity
@Table(name = "canales")
public class Canal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String descripcion;

    // El usuario que creó el canal
    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Usuario creador;

    // Si borramos un canal, se borran sus mensajes (CascadeType.ALL)
    @OneToMany(mappedBy = "canal", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes = new ArrayList<>();

    public Canal() {}

    public Canal(String nombre, String descripcion, Usuario creador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Usuario getCreador() { return creador; }
    public void setCreador(Usuario creador) { this.creador = creador; }
    public List<Mensaje> getMensajes() { return mensajes; }
    public void setMensajes(List<Mensaje> mensajes) { this.mensajes = mensajes; }
}