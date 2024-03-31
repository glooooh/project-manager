package com.projectmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.projectmanager.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{

}
