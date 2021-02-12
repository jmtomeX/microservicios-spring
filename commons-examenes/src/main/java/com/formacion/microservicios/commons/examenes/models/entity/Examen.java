package com.formacion.microservicios.commons.examenes.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// agregar la dependencia en pom.xml, se agrega de forma automática cuando se crea el proyecto
// con spring-starter-web en este caso al no incluirlo hay que insertarlo manualmente
/*
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-json</artifactId>
</dependency>
*/
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "examenes")
public class Examen {
	@Id
	// autoincremental
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty // valida Strings
	@Size(min = 4, max = 30) // validar tamaño de la cadena
	private String nombre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;


	// para evitar la relación inversa y evitar un bucle infinito en la
	// petición(suprimir atributo examen)
	// permitir setters para poder asignar la relación inversa
	@JsonIgnoreProperties(value = { "examen" , "handler", "hibernateLazyInitializer"}, allowSetters = true)
	// establecer la relación con examenes bidireccional, en cascascada, borra las
	// preguntas huerfanas
	@OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pregunta> preguntas;

	// relación con asignaturas
	@JsonIgnoreProperties(value= {"handler", "hibernateLazyInitializer"}) // lo mismo en la relación en Asignatura
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull // valida objetos para validar
	private Asignatura asignaturaPadre;
	
	@JsonIgnoreProperties(value= {"handler", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull // valida objetos para validar
	private Asignatura asignaturaHija;

	// no mapeado en la bbdd porque se le asignaría a todos los alumnos
	@Transient
	private boolean respondido;

	// constructor
	public Examen() {
		this.preguntas = new ArrayList<>();
	}

	// inicializar la fecha
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}
	

	public Asignatura getAsignaturaPadre() {
		return asignaturaPadre;
	}

	public void setAsignaturaPadre(Asignatura asignaturaPadre) {
		this.asignaturaPadre = asignaturaPadre;
	}

	public Asignatura getAsignaturaHija() {
		return asignaturaHija;
	}

	public void setAsignaturaHija(Asignatura asignaturaHija) {
		this.asignaturaHija = asignaturaHija;
	}

	public boolean isRespondido() {
		return respondido;
	}

	public void setRespondido(boolean respondido) {
		this.respondido = respondido;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		// resetear preguntas con el clear para que tenga la referencia de los objetos
		// que se eliminaron
		this.preguntas.clear();
		// cada pregunta asignar el examen
		preguntas.forEach(p -> {
			this.addPregunta(p);
		});
		// lambda reducido
		// 1º preguntas.forEach(p -> this.addPregunta(p));
		// de esta forma se asume que lo que se recibe en this se pasa como argumento a
		// addPregunta
		// 2º preguntas.forEach(this::addPregunta)
	}

	// agregar pregunta al examen
	public void addPregunta(Pregunta pregunta) {
		this.preguntas.add(pregunta);
		// asignar el examen a la pregunta
		pregunta.setExamen(this);
	}

	public void removePregunta(Pregunta pregunta) {
		this.preguntas.remove(pregunta);
		// quitar la referencia al examen dejándola huérfana por lo que orphanRemoval la eliminará
		pregunta.setExamen(null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// comprobar que sean una instancia del mismo tipo examen
		if (!(obj instanceof Examen)) {
			return false;
		}
		// comparar los ids casteando a examen
		Examen a = (Examen) obj;
		return this.id != null && this.id.equals(a.getId());

	}

}
