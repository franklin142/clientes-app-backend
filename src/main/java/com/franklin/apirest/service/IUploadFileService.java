package com.franklin.apirest.service;

import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public interface IUploadFileService {
	public Resource getFile(String nombreFoto, String path)throws MalformedURLException;
	public String saveFile(@RequestParam MultipartFile archivo)throws IOException;
	public Boolean deleteFile(String nombreFoto);
	public Path getPath(String nombreFoto);
	public Path getPath(String nombreFoto,String path);
}
