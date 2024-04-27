package com.projectmanager.service;

import java.util.Collection;

import org.kohsuke.github.GHRepository;

import com.projectmanager.entities.Tarefa;
import com.projectmanager.forms.TarefaForm;

public interface TarefaService {
    public Iterable<Tarefa> findAll();
    public Tarefa find(int id);
    public Tarefa save(TarefaForm tarefa, int usuarioid, GHRepository repo);
    public void delete(int id);
    public Collection<Tarefa> getTaskByProject(int projetoid);
}
