package com.formacion.microservicios.app.cursos.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// clase intermedia entre curso y alumno

@Entity
@Table
public class CursoAlumno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "alumno_id", unique= true)
	private Long alumnoId;
	
	// evitar bucle infinito que vuelva a hacer la llamada al hijo
	@JsonIgnoreProperties(value = {"cursoAlumnos"})
	@ManyToOne(fetch = FetchType.LAZY)
	// nombre de la ForengKey
	@JoinColumn(name = "curso_id")
	private Curso curso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// comprobar que sean una instancia del mismo tipo Alumno
		if (!(obj instanceof CursoAlumno)) {
			return false;
		}
		// comparar los ids casteando a Alumno
		CursoAlumno cA = (CursoAlumno) obj;
		return this.alumnoId != null && this.alumnoId.equals(cA.getAlumnoId());
	}
	
	
	
	
	
}
