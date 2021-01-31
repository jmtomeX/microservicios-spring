package com.formacion.microservicios.app.respuestas.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.formacion.microservicios.commons.examenes.models.entity.Examen;

@FeignClient(name = "microservicio-examenes")
public interface ExamenFeignClient {
	// EndPoints que se quieren consumir con Feign
	//se necesitan las preguntas para obtener los ids para obtener las respuestas
	@GetMapping("/{id}")
	public Examen obetenerExamenPorId(@PathVariable Long id);
	
	@GetMapping("/respondidos-por-preguntas")
	public List<Long> obtenerExmanesIdsPorPreguntasRespondidas (@RequestParam List<Long> preguntaIds);
	
	
	}
