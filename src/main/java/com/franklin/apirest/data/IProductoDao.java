package com.franklin.apirest.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.franklin.apirest.model.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{
	// Busca el resultado utilizando query de spring boot
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String nombre);
	
	// Revisar la documentacin de Spring Boot queryMethods para mas detalle
	// Genera el query segun el nombre del metodo
	public List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
