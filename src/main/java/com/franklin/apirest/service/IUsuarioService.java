package com.franklin.apirest.service;

import com.franklin.apirest.model.Usuario;

public interface IUsuarioService {
	public Usuario findByUsername(String username);
}
