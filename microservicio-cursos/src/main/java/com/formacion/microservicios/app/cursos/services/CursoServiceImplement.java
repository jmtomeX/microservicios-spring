package com.formacion.microservicios.app.cursos.services;

import org.springframework.stereotype.Service;

import com.formacion.microservicios.app.cursos.models.entity.Curso;
import com.formacion.microservicios.app.cursos.models.repository.CursoRepository;
import com.formacion.microservicios.commons.services.CommonServiceImplement;

// para que se pueda inyectar en el controlador
@Service
public class CursoServiceImplement extends CommonServiceImplement<Curso, CursoRepository> implements CursoService {



}
