package com.franklin.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.franklin.apirest.model.Cliente;
import com.franklin.apirest.model.Factura;
import com.franklin.apirest.model.Producto;
import com.franklin.apirest.model.Region;
import com.franklin.apirest.data.IClienteDao;
import com.franklin.apirest.data.IFacturaDao;
import com.franklin.apirest.data.IProductoDao;
/**
 * 
 * Implementa todos los metodos definidos en la interfaz y devuelve
 * los resultados de los metodos del crudRepository instanciado en
 * IClienteDao de la capa de datos
 * 
 * Esta es la capa de logica de negocios o clase de servicio
 */
@Service//Convierte una clase en servicio y la hace inyectable en controllers
public class ClienteServiceImpl implements IClienteService {
	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Autowired
	private IProductoDao productoDao;
	@Override
	@Transactional()//Para manejar las transacciones y hacer rollback si un error de constraints ocurre
	public List<Cliente> findAll() {
		return (List<Cliente>)clienteDao.findAll();
	}
	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}
	@Override
	@Transactional()//Para manejar las transacciones y hacer rollback si un error de constraints ocurre
	public Cliente findById(Long id) {
		// TODO Auto-generated method stub
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional()//Para manejar las transacciones y hacer rollback si un error de constraints ocurre
	public Cliente save(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional()//Para manejar las transacciones y hacer rollback si un error de constraints ocurre
	public void delete(Long id) {
		// TODO Auto-generated method stub
		clienteDao.deleteById(id);
	}
	@Override
	@Transactional(readOnly=true)
	public List<Region> findAllRegiones() {
		// TODO Auto-generated method stub
		return clienteDao.findAllRegiones();
	}
	@Override
	@Transactional(readOnly=true)
	public Factura findFacturaById(Long id) {
		// TODO Auto-generated method stub
		return facturaDao.findById(id).orElse(null);
	}
	@Override
	@Transactional()
	public Factura saveFactura(Factura factura) {
		// TODO Auto-generated method stub
		return facturaDao.save(factura);
	}
	@Override
	@Transactional()
	public void deleteFacturaById(Long id) {
		// TODO Auto-generated method stub
		facturaDao.deleteById(id);
	}
	@Override
	@Transactional()
	public List<Factura> findFacturaAll() {
		// TODO Auto-generated method stub
		return  (List<Factura>)facturaDao.findAll();
	}
	@Override
	public List<Producto> findProductoByNombre(String nombre) {
		// TODO Auto-generated method stub
		return (List<Producto>)productoDao.findByNombreContainingIgnoreCase(nombre);
	}

}
