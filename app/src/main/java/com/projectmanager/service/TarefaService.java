package com.projectmanager.service;

import com.projectmanager.entities.Tarefa;

public interface TarefaService {
    public Iterable<Tarefa> findAll();
    public Tarefa find(int id);
    public Tarefa save(Tarefa tarefa);
    public void delete(int id);
}
