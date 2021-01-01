package com.formacion.microservicios.app.examenes.services;

import java.util.List;

import com.formacion.microservicios.commons.examenes.models.entity.Examen;
import com.formacion.microservicios.commons.services.CommonService;

public interface ExamenService extends CommonService<Examen>{
	public List<Examen> findByNombre(String term);
}
