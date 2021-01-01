package com.formacion.microservicios.app.examenes.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacion.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacion.microservicios.commons.examenes.models.entity.Examen;
import com.formacion.microservicios.commons.services.CommonServiceImplement;
@Service
public class ExamenServiceImplement extends CommonServiceImplement<Examen, ExamenRepository> implements ExamenService {

	@Override
	@Transactional(readOnly = true)
	public List<Examen> findByNombre(String term) {
		// retornamos el repositorio que es la lista de examenes
		return repository.findByNombre(term);
	}

}
