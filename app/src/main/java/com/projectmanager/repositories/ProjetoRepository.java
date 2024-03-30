package com.projectmanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.projectmanager.entities.Projeto;

public interface ProjetoRepository extends CrudRepository<Projeto, Integer>{

}
