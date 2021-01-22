package com.formacion.microservicios.app.usuarios.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

	// método personalizado para buscar un usuario, no es una consulta sql. Es una consulta JPAQL
	// se necesita pasar a mayúsculas
	//@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
	// con upper lo pasamos a mayúsculas y con concat lo creamos como un string
	@Query("select a from Alumno a where upper(a.nombre) like upper(concat('%',?1, '%')) or upper(a.apellido) like upper(concat('%',?1, '%'))")
	// constultar https://docs.spring.io/spring-data/jpa/docs/2.4.2/reference/html/#jpa.query-methods
	public List<Alumno> findByNombreOrApellido(String term);
}
