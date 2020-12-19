package com.formacion.microservicios.app.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// habilitar Eureka
@EnableEurekaServer

@SpringBootApplication
public class MicroserviciosEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosEurekaApplication.class, args);
	}

}
