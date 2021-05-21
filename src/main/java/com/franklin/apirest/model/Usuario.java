package com.franklin.apirest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true,length=40)
	private String username;
	@Column(length=60)
	private String password;
	private boolean enabled;
	private String nombre;
	private String apellido;
	@Column(unique=true)
	private String email;
	
	//Genera una tabla intermedia para que un usuario pueda tener 
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	//medante @JoinTable se puede cambiar el nombre y los atributos de la tabla generada
	//si no se especifica esta notacion entonces se genera todo automaticamente
	//JoinCollumns sirve para generar la columna que une con usuarios
	//inverseJoinColumns sirve para generar la columna que une con roles
	//uniqueConstraints sirve para agregar indices unicos donde podemos
	//hacer que un registro no se repita si el @UniqueConstraint se cumple
	@JoinTable(name="usuarios_roles",
			   joinColumns = @JoinColumn(name="user_id"),
			   inverseJoinColumns = @JoinColumn(name="role_id"),
			   uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})})
	private List<Role> roles;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
