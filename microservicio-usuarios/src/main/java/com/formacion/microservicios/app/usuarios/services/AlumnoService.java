package com.formacion.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno>{

	public List<Alumno> findByNombreOrApellido(String term);
	
	public Iterable<Alumno> findAllById(Iterable<Long> ids);
	
	public void eliminarAlumnoCursoPorId(Long id);
	
	
}
