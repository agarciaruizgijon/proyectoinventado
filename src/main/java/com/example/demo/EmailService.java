package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**
 * Servicio encargado de gestionar el envío de correos electrónicos.
 * Utiliza la configuración SMTP de Google definida en application.properties.
 * * @author Alejandro
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Construye y envía un correo electrónico.
     * * @param destinatario La dirección de correo de la persona que lo va a recibir.
     * @param asunto El título o asunto del correo.
     * @param cuerpo El mensaje o contenido del correo.
     */
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        
        mensaje.setFrom("alejandrospringchat@gmail.com");
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        
        mailSender.send(mensaje);
        System.out.println("¡Correo enviado con éxito a " + destinatario + "!");
    }
}