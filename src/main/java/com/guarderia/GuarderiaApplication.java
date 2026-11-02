package com.guarderia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase Principal y Punto de Entrada del Sistema de Gestión de la Guardería.
 *
 * La anotación @SpringBootApplication activa tres características clave de forma automática:
 * 1. @EnableAutoConfiguration: Configura Hibernate y la conexión a MySQL según las propiedades.
 * 2. @ComponentScan: Escanea este paquete y todos sus subpaquetes (model, repository, service, controller)
 *    para registrar e inyectar automáticamente todos los componentes anotados (@Controller, @Service, @Repository).
 * 3. @SpringBootConfiguration: Permite registrar configuraciones adicionales en el contexto.
 */

@SpringBootApplication
public class GuarderiaApplication {

	// Inicia el motor de Spring Boot y levanta la aplicación completa
	public static void main(String[] args) {
		SpringApplication.run(GuarderiaApplication.class, args);
	}

}
