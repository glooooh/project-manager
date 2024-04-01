package com.projectmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projectmanager.entities.Tarefa;

@Repository("tarefaRepository")
public interface TarefaRepository extends CrudRepository<Tarefa, Integer>{

}
