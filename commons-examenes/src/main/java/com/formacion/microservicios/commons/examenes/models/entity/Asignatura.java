package com.formacion.microservicios.commons.examenes.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
public class Asignatura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	// asignaturas padres e hijas, (self join) asignatura referenciada hacia si
	// misma

	@JsonIgnoreProperties(value = { "hijos" })
	@ManyToOne(fetch = FetchType.LAZY)
	private String padre;

	@JsonIgnoreProperties(value = { "padre" }, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "padre", cascade = CascadeType.ALL)
	private List<Asignatura> hijos;

	// constuctor
	public Asignatura() {
		this.hijos = new ArrayList<>();
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

	public String getPadre() {
		return padre;
	}

	public void setPadre(String padre) {
		this.padre = padre;
	}

	public List<Asignatura> getHijos() {
		return hijos;
	}

	public void setHijos(List<Asignatura> hijos) {
		this.hijos = hijos;
	}
	

}
