package com.formacion.microservicios.app.usuarios.services;

import org.springframework.stereotype.Service;
import com.formacion.microservicios.app.usuarios.models.entity.Alumno;
import com.formacion.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.formacion.microservicios.commons.services.CommonServiceImplement;

@Service
public class AlumnoServiceImplement extends CommonServiceImplement<Alumno, AlumnoRepository> implements AlumnoService {

}
