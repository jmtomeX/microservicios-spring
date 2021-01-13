package com.formacion.microservicios.app.respuestas.services;

import org.springframework.data.jpa.repository.Query;

import com.formacion.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {
	// metódo del CRUD de spring
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
}
