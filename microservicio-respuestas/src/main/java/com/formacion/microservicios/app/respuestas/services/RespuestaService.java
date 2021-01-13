package com.formacion.microservicios.app.respuestas.services;

import com.formacion.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {
	// metódo del CRUD de spring
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
