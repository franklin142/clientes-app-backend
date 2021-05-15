package com.franklin.apirest.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.franklin.apirest.model.Cliente;
import com.franklin.apirest.model.Region;


public interface IClienteDao extends JpaRepository<Cliente, Long>{
	/**
	 * Sirve para llamar los metodos de spring data ya definidos
	 * por el framework, aqui se pueden escribir los metodos personalizados 
	 * de inserción, eliminacion y consulta utilizando el lenguaje de queries de 
	 * spring sql 
	 */
	
	/**
	 * Podemos agregar metodos que retornen datos de otra entidad que no 
	 * sea Cliente simplemente agregando la firma del metodo aqui
	 */
	@Query("from Region")
	public List<Region> findAllRegiones();
}
