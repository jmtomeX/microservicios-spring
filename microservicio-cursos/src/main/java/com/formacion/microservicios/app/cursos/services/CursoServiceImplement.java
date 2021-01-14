package com.formacion.microservicios.app.cursos.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.microservicios.app.cursos.clients.RespuestaFeignClient;
import com.formacion.microservicios.app.cursos.models.entity.Curso;
import com.formacion.microservicios.app.cursos.models.repository.CursoRepository;
import com.formacion.microservicios.commons.services.CommonServiceImplement;

// para que se pueda inyectar en el controlador
@Service
public class CursoServiceImplement extends CommonServiceImplement<Curso, CursoRepository> implements CursoService {

	@Autowired
	private RespuestaFeignClient client;
	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return repository.findCursoByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
		return client.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
	}



}
