package com.franklin.apirest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franklin.apirest.model.Cliente;
import com.franklin.apirest.data.IClienteDao;
/**
 * 
 * Implementa todos los metodos definidos en la interfaz y devuelve
 * los resultados de los metodos del crudRepository instanciado en
 * IClienteDao de la capa de datos
 * 
 * Esta es la capa de logica de negocios o clase de servicio
 */
@Service//Convierte en servicio una clase y la hace inyectable
public class ClienteServiceImpl implements IClienteService {
	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional()
	public List<Cliente> findAll() {
		return (List<Cliente>)clienteDao.findAll();
	}

	@Override
	@Transactional()
	public Cliente findById(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional()
	public Cliente save(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional()
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteDao.deleteById(id);
	}

}
