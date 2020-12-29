package com.formacion.microservicios.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
// para que encuentre la clase y añadirla en el pom como dependencia, se le añade la propia de cursos porque se sobreescribe
@EntityScan({ "com.formacion.microservicios.commons.alumnos.models.entity",
		"com.formacion.microservicios.app.cursos.models.entity" })
public class MicroservicioCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioCursosApplication.class, args);
	}

}
