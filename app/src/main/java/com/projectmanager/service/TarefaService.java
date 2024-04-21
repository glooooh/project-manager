package com.projectmanager.service;

import java.util.Collection;

import com.projectmanager.entities.Tarefa;

public interface TarefaService {
    public Iterable<Tarefa> findAll();
    public Tarefa find(int id);
    public Tarefa save(Tarefa tarefa, int usuarioid);
    public void delete(int id);
    public Collection<Tarefa> getTaskByProject(int projetoid);
}
