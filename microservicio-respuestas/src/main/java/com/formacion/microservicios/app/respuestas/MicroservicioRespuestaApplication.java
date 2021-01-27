package com.formacion.microservicios.app.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
// Eliminado todo el EntityScan al usar Mongo
// para que JPA encuentre las clases examen, alumno, respuesta ya que tienen una relación
//@EntityScan({ "com.formacion.microservicios.app.respuestas.models.entity",
	// ALUMNOS ES TRANSIENT NO ES NECESARIO	YA QUE SOLO ES UN ATRIBUTO YA NO ES UNA TABLA
		// "com.formacion.microservicios.commons.alumnos.models.entity",
		//"com.formacion.microservicios.commons.examenes.models.entity" })
public class MicroservicioRespuestaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioRespuestaApplication.class, args);
	}

}
