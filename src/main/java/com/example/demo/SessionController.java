package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * Controlador encargado de gestionar el inicio de sesión y el registro de usuarios.
 * * @author Alejandro
 */
@Controller
public class SessionController {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Para encriptar al registrar
    
    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String loginPage() {
        return "login"; 
    }

    /**
     * Registra un nuevo usuario en el sistema, encripta su contraseña y le envía
     * un correo electrónico de bienvenida.
     * * @param nombre El nombre de usuario elegido.
     * @param email El correo electrónico del usuario.
     * @param contrasena La contraseña en texto plano (se encriptará).
     * @param model Objeto para pasar mensajes de error o éxito a la vista.
     * @return La vista "login" recargada con un mensaje de estado.
     */
    @PostMapping("/register")
    public String registrarse(@RequestParam("nombre") String nombre, 
                              @RequestParam("email") String email, // Atrapar el email del HTML
                              @RequestParam("contrasena") String contrasena, Model model) {
        
        if (usuarioRepository.findByNombre(nombre) != null) {
            model.addAttribute("error", "¡Ese nombre de usuario ya existe!");
            return "login";
        }

        Usuario nuevo = new Usuario(nombre, passwordEncoder.encode(contrasena), email);
        usuarioRepository.save(nuevo);
        
        String asunto = "¡Bienvenido a SpringChat!";
        String cuerpo = "Hola " + nombre + ",\n\n¡Gracias por registrarte en SpringChat! Ya puedes iniciar sesión, entrar a los canales y empezar a hablar con otros usuarios.\n\nUn saludo,\nEl equipo de SpringChat.";
        
        // Llamamos al método que manda el email de verdad
        emailService.enviarCorreo(email, asunto, cuerpo);

        model.addAttribute("exito", "¡Cuenta creada! Revisa tu correo electrónico para ver la bienvenida y luego inicia sesión.");
        return "login";
    }

    @GetMapping("/noticias")
    public String mostrarNoticias(Model model, Principal principal) {
        // Principal contiene al usuario logueado por Spring Security
        List<Mensaje> listaNoticias = mensajeRepository.findAll();
        model.addAttribute("nombreUsuario", principal.getName());
        model.addAttribute("noticias", listaNoticias);
        return "noticias";
    }

}