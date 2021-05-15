package com.franklin.apirest.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service // Convierte una clase en servicio y la hace inyectable en controllers
public class UploadFileServiceImpl implements IUploadFileService {
	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	private final static String UPLOADS = "upload";

	@Override
	public Resource getFile(String nombreFoto, String path) throws MalformedURLException {
		Path rutaFoto = path.length() > 0 ? getPath(nombreFoto,path) : getPath(nombreFoto);
		Resource foto = new UrlResource(rutaFoto.toUri());
		// validamos si el archivo no existe o no puede ser leido
		if (!foto.exists() || !foto.isReadable()) {
			String noUserImage = "no-user-image.jpg";
			rutaFoto = Paths.get("src/main/resources/static/images").resolve(noUserImage).toAbsolutePath();
			foto = new UrlResource(rutaFoto.toUri());

			log.error("No se pudo cargar la imagen: " + nombreFoto);
		}
		return foto;
	}

	@Override
	public String saveFile(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");

		Path rutaArchivo = getPath(nombreArchivo);

		Files.copy(archivo.getInputStream(), rutaArchivo);

		return nombreArchivo;
	}

	@Override
	public Boolean deleteFile(String nombreFoto) {
		if(nombreFoto!=null && nombreFoto.length()>0) {
			Path rutaFotoOld = getPath(nombreFoto);
			File archivoOld = rutaFotoOld.toFile();
			//verificamos si el archivo existe y si puede ser leido
			if(archivoOld.exists() && archivoOld.canRead()) {
				archivoOld.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {
		// TODO Auto-generated method stub
		return Paths.get(UPLOADS).resolve(nombreFoto).toAbsolutePath();
	}
	@Override
	public Path getPath(String nombreFoto, String path) {
		// TODO Auto-generated method stub
		return Paths.get(path).resolve(nombreFoto).toAbsolutePath();
	}

}
