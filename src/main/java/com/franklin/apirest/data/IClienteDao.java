package com.franklin.apirest.data;

import org.springframework.data.repository.CrudRepository;

import com.franklin.apirest.model.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
	/**
	 * Sirve para llamar los metodos de spring data ya definidos
	 * por el framework, aqui se pueden escribir los metodos personalizados 
	 * de inserción, eliminacion y consulta utilizando el lenguaje de queries de 
	 * spring sql 
	 */
}
