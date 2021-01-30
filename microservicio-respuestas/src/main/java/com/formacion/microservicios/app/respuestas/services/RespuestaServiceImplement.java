package com.formacion.microservicios.app.respuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacion.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.formacion.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacion.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.formacion.microservicios.commons.examenes.models.entity.Examen;
import com.formacion.microservicios.commons.examenes.models.entity.Pregunta;

// para que se pueda registrar como componente de Spring
@Service
public class RespuestaServiceImplement implements RespuestaService {

	@Autowired // inyección
	private RespuestaRepository repository;

	@Autowired // inyección
	private ExamenFeignClient examenClient;

	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		// buscar el examen através del microservicio. FeingClient
		Examen examen = examenClient.obetenerExamenPorId(examenId);
		// obtener las preguntas através del examen
		List<Pregunta> preguntas = examen.getPreguntas();
		// convertir a un iterable con los ids de las preguntas
		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
		// respuestas
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByPreguntaIds(alumnoId,
				preguntaIds);
		// asignar cada pregunta a cada respuesta
		respuestas = respuestas.stream().map(r -> {
			preguntas.forEach(p -> {
				if (p.getId() == r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			// retorna la respuesta modificada con el obj pregunta
			return r;
			// retorna una lista de respuestas modificadas con el stream el flujo oroginal
			// no cambia
		}).collect(Collectors.toList()); // convertimos a tipo list
		return respuestas;
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		return null;
	}

}
