package com.projectmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projectmanager.entities.Projeto;

@Repository("projetoRepository")
public interface ProjetoRepository extends CrudRepository<Projeto, Integer>{

}
