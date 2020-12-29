package com.formacion.microservicios.app.usuarios.services;

import java.util.List;

import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno>{

	public List<Alumno> findByNombreOrApellido(String term);
	
	
}
