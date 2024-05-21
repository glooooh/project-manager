package com.projectmanager.service;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;

import com.projectmanager.entities.Tarefa;
import com.projectmanager.exceptions.BusinessException;
import com.projectmanager.forms.TarefaForm;

public interface TarefaService {
    public Iterable<Tarefa> findAll();
    public Tarefa find(int id);
    public Tarefa save(TarefaForm tarefaForm, String repoName, String accessToken, String user_id) throws IOException,BusinessException,DateTimeParseException;
    public Tarefa save(GHIssue issue,GHRepository repo)throws IOException;
    public Tarefa edit(TarefaForm tarefa, int tarefaId, GHRepository repo);
    public void delete(int id);
    public TarefaForm getFormTarefa(int id);
    public Collection<Tarefa> getTaskByProject(int projetoid);
    public List<String> getCollaboratorsUsernames(Tarefa tarefa);
}
