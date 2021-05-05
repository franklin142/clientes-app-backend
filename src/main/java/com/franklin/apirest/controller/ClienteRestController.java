package com.franklin.apirest.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.franklin.apirest.data.IClienteDao;
import com.franklin.apirest.model.Cliente;
import com.franklin.apirest.service.IClienteService;

@RestController //aplicaciones web utilizan Controller pero Apis Rest utilizan RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})//Limita los origenes que estan haciendo peticiones a la API rest
public class ClienteRestController {
	 /**se debe importar la interfaz del servicio generica y no ClienteServiceImpl
	  * ya que este es solo un componente de la interfaz principal, una extencion.
	  * Si existiera mas de una implementacion entonces se necesita hacer un qualifier
	  */
	@Autowired
	private IClienteService clienteService;
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
	}
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		//manejamos un posible error de la base de datos
		try {
			cliente =clienteService.findById(id);
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos");
			response.put("error", ex.getMostSpecificCause().getMessage());//obtenemos la causa mas especifica del error
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		if(cliente==null) {
			response.put("message", "El cliente que intenta cargar no existe");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
	}
	@PostMapping("/clientes")
	//@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		Map<String, Object> response = new HashMap<>();
		try {
			cliente = clienteService.save(cliente);
			response.put("message", "Cliente guardado correctamente");
			// esta es una manera de pasar el objeto a travez de un Map<> que al final se convierte en json
			response.put("cliente", cliente);
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos al insertar");
			response.put("error", ex.getMessage()+": "+ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	@PutMapping("/clientes/{id}")
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody Cliente cliente,@PathVariable Long id) {
		
		Cliente clienteFound = null;
		Map<String, Object> response = new HashMap<>();

		try {
			clienteFound =clienteService.findById(id);
			if(clienteFound==null) {
				response.put("message", "El cliente que intenta actualizar no existe");
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
			}
			clienteFound.setApellido(cliente.getApellido());
			clienteFound.setNombre(cliente.getNombre());
			clienteFound.setEmail(cliente.getEmail());
			cliente = clienteService.save(clienteFound);		
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos al actualizar el registro");
			response.put("error", ex.getMessage()+": "+ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/clientes/{id}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			clienteService.delete(id);
			response.put("message", "Cliente eliminado correctamente.");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos al borrar el registro");
			response.put("error", ex.getMessage()+": "+ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
}
