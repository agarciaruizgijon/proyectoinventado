package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie; // Importante para las cookies
import jakarta.servlet.http.HttpServletResponse; // Para enviar la cookie al navegador
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class SessionController {

	@Autowired
	private MensajeRepository mensajeRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	// LOGIN
	// Ahora comprueba si ya existen cookies para entrar directo
	@GetMapping("/")
	public String loginPage(@CookieValue(value = "cookie_nombre", defaultValue = "") String cNombre,
			@CookieValue(value = "cookie_pass", defaultValue = "") String cPass, HttpSession session, Model model) {

		// Si hay cookies guardadas hacemos login
		if (!cNombre.isEmpty() && !cPass.isEmpty()) {
			Usuario usuario = usuarioRepository.findByNombreAndContrasena(cNombre, cPass);
			if (usuario != null) {
				session.setAttribute("usuarioLogueado", usuario);
				return "redirect:/noticias";
			}
		}

		// Si no hay cookie mostramos el login normal
		return "login";
	}

	// PROCESAR LOGIN
	// Ahora guarda las cookies al entrar
	@PostMapping("/login")
	public String iniciarSesion(@RequestParam("nombre") String nombre, @RequestParam("contrasena") String contrasena,
			HttpSession session, HttpServletResponse response, // Necesario para enviar cookies
			Model model) {

		Usuario usuario = usuarioRepository.findByNombreAndContrasena(nombre, contrasena);

		if (usuario != null) {
			session.setAttribute("usuarioLogueado", usuario);

			// --- CREAR COOKIES ---
			// Guardamos nombre
			Cookie cookieNombre = new Cookie("cookie_nombre", nombre);
			cookieNombre.setMaxAge(7 * 24 * 60 * 60); // Duran 7 días
			cookieNombre.setPath("/"); // Disponibles en toda la web
			response.addCookie(cookieNombre);

			// Guardamos contraseña
			Cookie cookiePass = new Cookie("cookie_pass", contrasena);
			cookiePass.setMaxAge(7 * 24 * 60 * 60); // Duran 7 días
			cookiePass.setPath("/");
			response.addCookie(cookiePass);
			// ---------------------

			return "redirect:/noticias";
		} else {
			model.addAttribute("error", "Usuario o contraseña incorrectos");
			return "login";
		}
	}

	@PostMapping("/register")
	public String registrarse(@RequestParam("nombre") String nombre, @RequestParam("contrasena") String contrasena,
			Model model) {

		if (usuarioRepository.findByNombre(nombre) != null) {
			model.addAttribute("error", "¡Ese nombre de usuario ya existe!");
			return "login";
		}

		Usuario nuevo = new Usuario(nombre, contrasena);
		usuarioRepository.save(nuevo);
		model.addAttribute("exito", "¡Cuenta creada! Ahora inicia sesión.");
		return "login";
	}

	@GetMapping("/noticias")
	public String mostrarNoticias(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
		if (usuario == null) {
			return "redirect:/";
		}

		List<Mensaje> listaNoticias = mensajeRepository.findAll();
		model.addAttribute("nombreUsuario", usuario.getNombre());
		model.addAttribute("noticias", listaNoticias);
		return "noticias";
	}

	@PostMapping("/agregar-noticia")
	public String agregarNoticia(@RequestParam("texto") String texto, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
		if (usuario == null)
			return "redirect:/";

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yy");
		String fechaStr = formatter.format(new Date());

		Mensaje nuevoMensaje = new Mensaje();
		nuevoMensaje.setTexto(texto);
		nuevoMensaje.setFecha(fechaStr);
		nuevoMensaje.setUsuario(usuario);

		mensajeRepository.save(nuevoMensaje);
		return "redirect:/noticias";
	}

	// --- 3. LOGOUT (MODIFICADO) ---
	// Borra las cookies y la sesión
	@GetMapping("/deletecookie") // Mantenemos la ruta que pediste
	public String logout(HttpServletResponse response, HttpSession session) {

		// Sobrescribimos la cookie del nombre para que caduque YA
		Cookie cNombre = new Cookie("cookie_nombre", null);
		cNombre.setMaxAge(0); // 0 segundos de vida = borrar
		cNombre.setPath("/");
		response.addCookie(cNombre);

		// Sobrescribimos la cookie de la contraseña
		Cookie cPass = new Cookie("cookie_pass", null);
		cPass.setMaxAge(0);
		cPass.setPath("/");
		response.addCookie(cPass);

		// Borramos la sesión
		session.invalidate();

		return "redirect:/";
	}
}