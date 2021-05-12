package com.franklin.apirest.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {// Serializable sirve para poder guardar el objeto en sesion con sus atributos serializados.

	private static final long serialVersionUID = 1L;// Esta linea es necesaria cuando se implementa serializable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min=4,max=50)
	@Column(nullable = false)
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	@Column(nullable = false,unique = true)
	private String email;
	
	@NotNull
	@Column(name="created_at")
	private LocalDate createdAt;
	
	private String foto;
	
	/*
	@PrePersist
	private void prePersist() {
		//Esto es un evento que se ejecuta antes de guardar el modelo en la base de datos
		createdAt = LocalDate.now();
	}
	*/
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
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
