package com.formacion.microservicios.app.cursos.services;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.formacion.microservicios.app.cursos.models.entity.Curso;
import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {

	public Curso findCursoByAlumnoId(Long id);
	
	// petición al microservicio respuesta
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno( Long alumnoId);
	
	// petición al microservicio Alumnos
	public Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
	
	
}
