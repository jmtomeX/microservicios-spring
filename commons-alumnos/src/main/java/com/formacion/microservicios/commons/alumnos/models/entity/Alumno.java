package com.formacion.microservicios.commons.alumnos.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "alumnos")
public class Alumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(min = 4, max = 30) // validar tamaño de la cadena
	private String nombre;

	@NotEmpty
	private String apellido;

	@NotEmpty
	@Email
	private String email;

	@Column(name = "create_at")
	// pasar a timeStamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	// perisistir archivo BLOB(Binary Large Objects, objetos binarios grandes) en la
	// bbdd
	@Lob
	@JsonIgnore // ignorar el atributo en el json hay que importar Json en el pom.xml
	private byte[] foto;

	// asignar fecha antes de hacer un insert asigne la fecha
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	// método para tener un identificador de la foto para Angular
	public Integer getPhotoHashCode() {
		// validar que la foto sea distinta de null
		return (this.foto != null) ? this.foto.hashCode() : null;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Override
	// Eliminar un objeto de la relación con la entidad curso, método para compobrar
	// que sea el mismo alumno, buscando en la lista de la clase curso.
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		// comprobar que sean una instancia del mismo tipo Alumno
		if (!(obj instanceof Alumno)) {
			return false;
		}
		// comparar los ids casteando a Alumno
		Alumno a = (Alumno) obj;
		return this.id != null && this.id.equals(a.getId());
	}

}
