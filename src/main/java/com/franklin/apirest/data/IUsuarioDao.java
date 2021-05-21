package com.franklin.apirest.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.franklin.apirest.model.Usuario;
//CrudRepository no admite paginacion, jpaRepository si

public interface IUsuarioDao extends CrudRepository<Usuario,Long>{
	/** 
	 * consulta a travez del nombre del metodo donde 
	 * find es el tipo de consulta, By equivale a la condicion where
	 * y Username el nombre del campo iniciando con mayuscula,
	 * es importante que el nombre del parametro vaya en orden si 
	 * se necesita filtrar por mas de un campo, 
	 * ej: findByNameAndEmail(String username,String email)
	 * @param name
	 * @return
	 */
	public Usuario findByUsername(String username);
	
	/**
	 * Busca registros basado en un Spring Data Query 
	 * el cual evalua los parametros en orden de llegada
	 * para filtrar el resultado
	 * @param username
	 * @return
	 */
	@Query("select u from Usuario u where u.username = ?1")
	public Usuario findBylogin(String username);
}
