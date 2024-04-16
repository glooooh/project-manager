package com.projectmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projectmanager.entities.Colaborador;
import com.projectmanager.entities.ParametroId;

@Repository("colaboradorRepository")
public interface ColaboradorRepository extends CrudRepository<Colaborador, ParametroId>{
    
}
