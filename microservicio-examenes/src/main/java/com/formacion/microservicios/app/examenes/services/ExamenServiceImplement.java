package com.formacion.microservicios.app.examenes.services;

import org.springframework.stereotype.Service;

import com.formacion.microservicios.app.examenes.models.entity.Examen;
import com.formacion.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacion.microservicios.commons.services.CommonServiceImplement;
@Service
public class ExamenServiceImplement extends CommonServiceImplement<Examen, ExamenRepository> implements ExamenService {

}
