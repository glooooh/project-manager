package com.projectmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projectmanager.entities.Comentario;

@Repository("comentarioRepository")
public interface ComentarioRepository extends CrudRepository<Comentario, Integer>{

}
