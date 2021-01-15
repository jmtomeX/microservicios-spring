package com.formacion.microservicios.app.cursos.services;

import com.formacion.microservicios.app.cursos.models.entity.Curso;
import com.formacion.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {

	public Curso findCursoByAlumnoId(Long id);
	
	// petición al microservicio respuesta
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno( Long alumnoId);
}
