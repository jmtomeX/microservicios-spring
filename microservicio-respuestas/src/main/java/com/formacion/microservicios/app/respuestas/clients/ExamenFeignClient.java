package com.formacion.microservicios.app.respuestas.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.formacion.microservicios.commons.examenes.models.entity.Examen;

@FeignClient(name = "microservicio-examenes")
public interface ExamenFeignClient {
	
	//se necesitan las preguntas para obtener los ids para obtener las respuestas
	@GetMapping("/{id}")
	public Examen obetenerExamenPorId(@PathVariable Long id);
}
