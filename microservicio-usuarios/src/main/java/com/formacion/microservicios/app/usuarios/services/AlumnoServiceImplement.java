package com.formacion.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.services.CommonServiceImplement;

@Service
public class AlumnoServiceImplement extends CommonServiceImplement<Alumno, AlumnoRepository> implements AlumnoService {

	@Override
	// como es de consulta solo lee
	@Transactional(readOnly= true)
	public List<Alumno> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}

}
