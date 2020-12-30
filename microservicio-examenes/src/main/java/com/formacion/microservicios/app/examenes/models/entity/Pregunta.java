package com.formacion.microservicios.app.examenes.models.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "preguntas")
public class Pregunta {
	@Id
	// autoincremental
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String texto;

	// para evitar la relación inversa y evitar un bucle infinito en la
	// petición(suprimir atributo preguntas)
	@JsonIgnoreProperties(value = { "preguntas" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examen_id")
	private Examen examen;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	///comprobar que las preguntas son las mismas comprobando el id
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}
		// comprobar que sean una instancia del mismo tipo Pregunta
		if(!(obj instanceof Pregunta)) {
			return false;
		}
		//comparar los ids casteando a pregunta
		Pregunta a = (Pregunta) obj;
		return this.id != null && this.id.equals(a.getId());
		
	
	}
	
	

	
}
