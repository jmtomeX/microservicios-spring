package com.formacion.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.microservicios.app.usuarios.client.CursoFeignClient;
import com.formacion.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.services.CommonServiceImplement;

@Service
public class AlumnoServiceImplement extends CommonServiceImplement<Alumno, AlumnoRepository> implements AlumnoService {

	@Autowired
	private CursoFeignClient clientCurso;
	@Override
	// como es de consulta solo lee
	@Transactional(readOnly= true)
	public List<Alumno> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional(readOnly= true)
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		
		return repository.findAllById(ids) ;
	}

	// cliente HTTP
	@Override
	public void eliminarAlumnoCursoPorId(Long id) {
		clientCurso.eliminarAlumnoCursoPorId(id);
		
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		// después de que borre en postgre
		// eliminamos mediante API Rest utilizando feign de mysql de la tabla curso-alumnos
		// si hay algún error de comunicación con el servicio externo no se eliminaría de postgre.
		this.eliminarAlumnoCursoPorId(id);
	}
	

}
