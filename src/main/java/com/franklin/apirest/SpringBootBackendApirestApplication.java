package com.franklin.apirest;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Command line runer sirve para correr lineas antes de arrancar la app
@SpringBootApplication
public class SpringBootBackendApirestApplication implements CommandLineRunner{
	@Autowired 
	public BCryptPasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApirestApplication.class, args);
	}
	//run sirve para ordenar el codigo que se va a ejecutar antes que se levante el server
	@Override
	public void run(String... args) throws Exception {
		String password ="1234";
		String passwordBCrypt="";
		for (int i=0; i <4;i++) {
			passwordBCrypt = passwordEncoder.encode(password);
			System.out.println(passwordBCrypt);
		}
	}

}
