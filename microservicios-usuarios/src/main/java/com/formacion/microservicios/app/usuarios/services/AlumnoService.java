package com.formacion.microservicios.app.usuarios.services;

import java.util.Optional;

import com.formacion.microservicios.app.usuarios.models.entity.Alumno;

public interface AlumnoService {
	//listar alumnos
	public Iterable<Alumno> findAll();

	// listar por id
	public Optional<Alumno> findById(Long id);

	public Alumno save(Alumno alumno);

	public void deleteById(Long id);
}
