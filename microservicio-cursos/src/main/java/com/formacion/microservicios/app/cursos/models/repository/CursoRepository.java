package com.formacion.microservicios.app.cursos.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacion.microservicios.app.cursos.models.entity.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{
	// no es una sentencia SQL, es sentencia JPAQL http://javaespanol.blogspot.com/2015/12/introduccion-jpa-parte-iii-lenguaje-jpql.html
	// retornamos el curso c es el alias objeto, preguntar por el id del alumno alias a
	// @Query("select c from Curso c join fetch c.alumnos a where a.id=?1") relación con mysql que ya no está al pasar la bbdd a postgres
	@Query("select c from Curso c join fetch c.cursoAlumnos a where a.alumnoId=?1")
	public Curso findCursoByAlumnoId(Long id);
}
