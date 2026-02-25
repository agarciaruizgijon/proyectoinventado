package com.example.demo;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Controlador principal para la gestión de canales.
 * Controla la creación y borrado de los canales y sus mensajes.
 * * @author Alejandro
 */
@Controller
public class CanalController {

	@Autowired
	private CanalRepository canalRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MensajeRepository mensajeRepository;

	/**
     * Entra a una sala de chat específica y carga todo su historial de mensajes.
     * * @param id El identificador único del canal.
     * @param model Modelo para pasar datos a la vista Thymeleaf.
     * @param principal Contiene la información del usuario autenticado actualmente.
     * @return La plantilla HTML "chat-canal" o redirige si no existe.
     */
	@GetMapping("/canal/{id}")
	public String verCanal(@PathVariable("id") Long id, Model model, Principal principal) {
		
		Canal canal = canalRepository.findById(id).orElse(null);

		if (canal == null) {
			return "redirect:/canales"; 
		}

		List<Mensaje> mensajesDelCanal = mensajeRepository.findByCanalId(id);

		model.addAttribute("nombreUsuario", principal.getName());
		model.addAttribute("canal", canal);
		model.addAttribute("noticias", mensajesDelCanal);

		return "chat-canal"; 
	}

	/**
     * Añade un nuevo mensaje a un canal específico y lo guarda en la base de datos con la fecha actual.
     * * @param texto El contenido del mensaje escrito por el usuario.
     * @param canalId La ID del canal donde se está escribiendo.
     * @param principal Contiene la información del usuario autenticado.
     * @return Redirige de vuelta a la vista del canal para ver el nuevo mensaje.
     */	@PostMapping("/agregar-noticia")
	public String agregarNoticia(@RequestParam("texto") String texto, @RequestParam("canalId") Long canalId,
			Principal principal) {

		Usuario usuario = usuarioRepository.findByNombre(principal.getName());
		Canal canal = canalRepository.findById(canalId).orElse(null);

		if (canal != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yy");

			Mensaje nuevoMensaje = new Mensaje();
			nuevoMensaje.setTexto(texto);
			nuevoMensaje.setFecha(formatter.format(new Date()));
			nuevoMensaje.setUsuario(usuario);

			nuevoMensaje.setCanal(canal);

			mensajeRepository.save(nuevoMensaje);
		}

		return "redirect:/canal/" + canalId;
	}

	@GetMapping("/canales")
	public String verCanales(Model model, Principal principal) {
		
		Usuario usuarioLogueado = usuarioRepository.findByNombre(principal.getName());
		List<Canal> listaCanales = canalRepository.findAll();

		model.addAttribute("usuarioLogueado", usuarioLogueado);
		model.addAttribute("canales", listaCanales);

		return "canales";
	}

	/**
     * Crea un nuevo canal en la base de datos si el usuario no ha superado el límite.
     * * @param nombre El nombre visible de la sala.
     * @param descripcion De qué trata la sala.
     * @param principal El objeto de seguridad que contiene al usuario logueado.
     * @return Redirige a la lista de canales, o devuelve un parámetro de error si falla.
     */
	@PostMapping("/crear-canal")
	public String crearCanal(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
			Principal principal) {

		Usuario usuario = usuarioRepository.findByNombre(principal.getName());
		long cantidadCanales = canalRepository.countByCreador(usuario);

		if (cantidadCanales >= 3) {
			return "redirect:/canales?error=limite";
		}

		if (canalRepository.findByNombre(nombre) != null) {
			return "redirect:/canales?error=existe";
		}

		Canal nuevoCanal = new Canal(nombre, descripcion, usuario);
		canalRepository.save(nuevoCanal);

		return "redirect:/canales";
	}

	@PostMapping("/borrar-canal/{id}")
	public String borrarCanal(@PathVariable("id") Long canalId, Principal principal) {

		Canal canal = canalRepository.findById(canalId).orElse(null);
		Usuario usuarioLogueado = usuarioRepository.findByNombre(principal.getName());

		if (canal != null) {
			if (canal.getCreador().getId().equals(usuarioLogueado.getId())) {
				canalRepository.delete(canal);
			} else {
				return "redirect:/canales?error=nopermiso";
			}
		}

		return "redirect:/canales";
	}
}