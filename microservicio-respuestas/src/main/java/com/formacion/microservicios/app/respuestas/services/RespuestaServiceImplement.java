package com.formacion.microservicios.app.respuestas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacion.microservicios.app.respuestas.models.repository.RespuestaRepository;
// para que se pueda registrar como componente de Spring
@Service
public class RespuestaServiceImplement implements RespuestaService {

	@Autowired
	private RespuestaRepository repository;
	@Override
	@Transactional
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}
	@Override
	@Transactional(readOnly = true) // solo de lectura
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		return null;
	}
	@Override
	@Transactional(readOnly = true) // solo de lectura
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		return null;
	}

}
