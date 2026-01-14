package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoInventadoApplication {

	public static void main(String[] args) {
		// Esta línea es mágica: arranca la app y lee sola el application.properties
		SpringApplication.run(ProyectoInventadoApplication.class, args);
	}

}