package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Properties;

@SpringBootApplication
public class ProyectoInventadoApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ProyectoInventadoApplication.class);

		Properties properties = new Properties();

		// 1. CREDENCIALES
		properties.setProperty("spring.datasource.url",
				"jdbc:postgresql://ep-misty-fire-agvokqit-pooler.c-2.eu-central-1.aws.neon.tech/neondb?sslmode=require");
		properties.setProperty("spring.datasource.username", "neondb_owner");
		properties.setProperty("spring.datasource.password", "npg_RX15hKPmkHgL");
		properties.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

		// 2. LA LÍNEA MÁGICA: OBLIGAMOS A CREAR LAS TABLAS
		properties.setProperty("spring.jpa.hibernate.ddl-auto", "update");

		app.setDefaultProperties(properties);
		app.run(args);
	}

}