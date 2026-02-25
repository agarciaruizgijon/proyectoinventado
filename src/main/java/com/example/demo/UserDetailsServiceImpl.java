package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombre(nombre);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Spring Security maneja los usuarios con este objeto UserDetails
        return User.builder()
                .username(usuario.getNombre())
                .password(usuario.getContrasena())
                .roles("USER") // Rol por defecto
                .build();
    }
}