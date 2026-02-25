package com.example.demo;
/**
 * Clase de configuración de Spring Security.
 * Define las rutas públicas (como el login o la API), las rutas privadas y la encriptación de contraseñas.
 * * @author TuNombre
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				// ¡AQUÍ ESTÁ EL CAMBIO! Añadimos las rutas de la API y Swagger
				.requestMatchers("/", "/register", "/css/**", "/api/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() 
				.anyRequest().authenticated() 
		).formLogin(form -> form.loginPage("/") 
				.loginProcessingUrl("/login") 
				.usernameParameter("nombre") 
				.passwordParameter("contrasena")
				.defaultSuccessUrl("/canales", true) // si hace biwen el login, va hacia canales 
				.failureUrl("/?error=true") // Si falla va aqui
				.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/?logout=true")
						.deleteCookies("JSESSIONID").permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}