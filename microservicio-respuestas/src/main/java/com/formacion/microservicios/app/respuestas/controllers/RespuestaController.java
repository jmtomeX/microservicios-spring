package com.formacion.microservicios.app.respuestas.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacion.microservicios.app.respuestas.services.RespuestaService;

@RestController
public class RespuestaController {
	// se ocupa de guardar las respuestas
	@Autowired
	private RespuestaService service;

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas) {
		respuestas = ((List<Respuesta>)respuestas).stream().map(r ->  {
			// respuesta en el RequestBody como Json
			// relaciones con otras entitys
			r.setAlumnoId(r.getAlumno().getId());
			r.setPreguntaId(r.getPregunta().getId()); 
			return r;
		}).collect(Collectors.toList());
		Iterable<Respuesta> respuestasDb = service.saveAll(respuestas);
		// se guardan un conjunto de respuestas asociadas a las preguntas y asociadas al
		// examen
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
	}

	// que preguntas fueron respondidas por el alumno
	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	// se guarda en el responseEntity
	public ResponseEntity<?> obtenerRepuestasPorAlumnoPorExamen(@PathVariable Long alumnoId,
			@PathVariable Long examenId) {
		Iterable<Respuesta> respuestas = service.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return ResponseEntity.ok(respuestas);
	}

	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId) {
		Iterable<Long> examenesIds = service.findExamenesIdsConRespuestasByAlumno(alumnoId);
		return ResponseEntity.ok(examenesIds);

	}
}
