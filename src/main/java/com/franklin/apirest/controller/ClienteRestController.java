package com.franklin.apirest.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.franklin.apirest.model.Cliente;
import com.franklin.apirest.model.Region;
import com.franklin.apirest.service.IClienteService;
import com.franklin.apirest.service.IUploadFileService;

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
	@Autowired
	private IUploadFileService uploadFileService;
	private final Logger log = LoggerFactory.getLogger( ClienteRestController.class);
	
	@Secured({"ROLE_ADMIN","ROLE_SELER"})
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
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
	}
	@GetMapping("/clientes/page/{page}")
	//Devuelve una pagina determinada segun el numero que se reciba como parametro
	public Page<Cliente> index(@PathVariable Integer page){
		Integer size = 5;
		Pageable pageable = PageRequest.of(page,size);
		/* Esta funcion retorna una pagina con los datos siguientes
		 * content: La lista de datos de la pagina solicitada mediante @PathVariable.
		 * totalPages: Total de paginas existentes segun el size de registros por pagina.
		 * totalElements: Es el total de registros en la consulta.
		 * size: Es el total de registros por pagina.
		 * number: es el numero de pagina retornado al usuario.
		 * numberOfElements: Es el total de registros devueltos en la pagina actual.
		 * empty: retorna true o false para indicar que la pagina trae o no trae registros.
		 * first: indica si es la primer pagina de la consulta.
		 * last: indica si es la ultima pagina de la consulta.
		 */
		return clienteService.findAll(pageable);
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/clientes")
	//@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody @Valid Cliente cliente, BindingResult result) {
		//el interceptor @valid es el encargado de mapear los errores producidos
		//durante la conversion del @RequestBody a Cliente, por ende debe ir antes de él. 
		//Luego se inyecta el objeto que va a contener
		//todos los mensajes de error para poder retornarlos. El objeto BindingResult debe ir
		//justo después del objeto que estamos tansformando de lo contrario tendremos error.
		Map<String, Object> response = new HashMap<>();
		try {
			if(result.hasErrors()) {
				//obtenemos los errores de validacion de @valid a travez del objeto result
				List<String> errors = result.getFieldErrors()
						.stream()
						.map(err-> "'"+err.getField()+ "' "+err.getDefaultMessage())
						.collect(Collectors.toList());
				
				response.put("errors",errors);
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);	
			}
			cliente = clienteService.save(cliente);
			response.put("message", "Cliente guardado correctamente");
			// esta es una manera de pasar el objeto a travez de un Map<> que al final se convierte en json
			response.put("cliente", cliente);
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos al insertar");
			response.put("error", ex.getMessage()+": "+ex.getMostSpecificCause().getMessage());
			response.put("error2", result.toString());

			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/clientes/{id}")
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,
			BindingResult result, @PathVariable Long id) {
		
		Cliente clienteFound = null;
		Map<String, Object> response = new HashMap<>();

		try {
			if(result.hasErrors()) {
				//obtenemos los errores de validacion de @valid a travez del objeto result
				List<String> errors = result.getFieldErrors()
						.stream()
						.map(err-> "'"+err.getObjectName()+ "' "+err.getDefaultMessage())
						.collect(Collectors.toList());
				
				response.put("errors",errors);
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);	
			}
			clienteFound =clienteService.findById(id);
			if(clienteFound==null) {
				response.put("message", "El cliente que intenta actualizar no existe");
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
			}
			clienteFound.setApellido(cliente.getApellido());
			clienteFound.setNombre(cliente.getNombre());
			clienteFound.setEmail(cliente.getEmail());
			clienteFound.setCreatedAt(cliente.getCreatedAt());
			clienteFound.setRegion(cliente.getRegion());
			cliente = clienteService.save(clienteFound);		
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos al actualizar el registro");
			response.put("error", ex.getMessage()+": "+ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.CREATED);
		
	}
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/clientes/{id}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			Cliente cliente = clienteService.findById(id);
			
			//eliminamos la foto para que no quede huerfana despues de borrar el cliente
			uploadFileService.deleteFile(cliente.getFoto());
			
			clienteService.delete(id);
			
			response.put("message", "Cliente eliminado correctamente.");
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos al borrar el registro");
			response.put("error", ex.getMessage()+": "+ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_SELER"})
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam MultipartFile archivo, @RequestParam Long id){
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = clienteService.findById(id);
		log.info(id.toString());
		if(!archivo.isEmpty()) {
			String nombreArchivo = null;
			
			try {
				nombreArchivo = uploadFileService.saveFile(archivo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.put("message", "Error al subir la imagen");
				//response.put("error", e.getMessage()+": "+e.getCause().getMessage());
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			//eliminamos la foto anterior
		    uploadFileService.deleteFile(cliente.getFoto());
		    
			cliente.setFoto(nombreArchivo);
			
			clienteService.save(cliente);
			
			response.put("message", "Foto actualizada correctamente - "+archivo.getOriginalFilename());
			response.put("cliente", cliente);
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	//nombreFoto incluye una extencion de imagen, por lo que 
	//debe llevar la expresion regular :.+ para que no de error
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		Resource foto = null;
		try {
			foto = uploadFileService.getFile(nombreFoto,"");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		//Si todo es correcto agregamos la cabecera http para forzar descargar el archivo
		HttpHeaders cabecera = new HttpHeaders();
		
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+foto.getFilename()+"\"");
		return new ResponseEntity<Resource>(foto,cabecera,HttpStatus.OK);
	}
	@GetMapping("/static/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> getDefualtImage(@PathVariable String nombreFoto){		
		Resource foto=null;
		try {
			foto = uploadFileService.getFile(nombreFoto,"src/main/resources/static/images");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//validamos si el archivo no existe o no puede ser leido
		if(!foto.exists() || !foto.isReadable()) {
			throw new RuntimeException("No se pudo cargar la imagen: "+nombreFoto);
		}
		//Sito todo es correcto agregamos la cabecera http para forzar descargar el archivo
		HttpHeaders cabecera = new HttpHeaders();
		
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+foto.getFilename()+"\"");
		return new ResponseEntity<Resource>(foto,cabecera,HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_SELER"})
	@GetMapping("/clientes/regiones")
	public List<Region> listarRegiones(){
		List<Region> regiones = clienteService.findAllRegiones();
		//log.info(regiones.toString());
		return clienteService.findAllRegiones();
	}

	/*
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	*/
}
