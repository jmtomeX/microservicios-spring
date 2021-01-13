package com.formacion.microservicios.app.respuestas.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formacion.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends CrudRepository<Respuesta, Long> {

	// el join no es a campos de la tabla como en sql sino a atributos
	// el join fetch trae todos los datos relacionados con las tres tablas (alumno, examen, respuestas)
	// a.id=?1 and e.id=?2  alumno.id = parámetro 1 y examen.id = parámetro 2  
	// r.pregunta p = alias p
	@Query("select r from Respuesta r join fetch r.alumno a join fetch r.pregunta p join fetch p.examen e where a.id=?1 and e.id=?2")
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
}
