package com.formacion.microservicios.app.cursos.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.microservicios.app.cursos.clients.AlumnoFeignClient;
import com.formacion.microservicios.app.cursos.clients.RespuestaFeignClient;
import com.formacion.microservicios.app.cursos.models.entity.Curso;
import com.formacion.microservicios.app.cursos.models.repository.CursoRepository;
import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.services.CommonServiceImplement;

// para que se pueda inyectar en el controlador
@Service
public class CursoServiceImplement extends CommonServiceImplement<Curso, CursoRepository> implements CursoService {

	@Autowired
	private RespuestaFeignClient clientRespuesta;
	
	@Autowired
	private AlumnoFeignClient clientAlumno;
	
	
	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return repository.findCursoByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
		return clientRespuesta.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
	}

	@Override
	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
		return clientAlumno.obtenerAlumnosPorCurso(ids);
	}

	@Override
	@Transactional
	public void eliminarCursoAlumnoPorId(Long id) {
		repository.eliminarCursoAlumnoPorId(id);
	}
	
	



}
