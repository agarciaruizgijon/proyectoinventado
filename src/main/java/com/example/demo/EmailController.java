package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controlador REST que expone una API para enviar correos electrónicos de forma externa.
 * Accesible a través de herramientas como Postman o Swagger.
 * * @author Alejandro
 */
@RestController
@RequestMapping("/api/email") 
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    /**
     * Endpoint POST para disparar un correo electrónico mediante parámetros en la URL.
     * * @param to Dirección de correo del destinatario.
     * @param subject Asunto del correo.
     * @param body Cuerpo o texto principal del mensaje.
     * @return Un mensaje de confirmación.
     */
    @PostMapping("/send")
    public String enviarCorreoRest(@RequestParam String to, 
                                   @RequestParam String subject, 
                                   @RequestParam String body) {
        
        emailService.enviarCorreo(to, subject, body);
        
        return "Correo enviado correctamente a " + to;
    }
}