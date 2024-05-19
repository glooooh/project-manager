package com.projectmanager.service;

import java.util.Collection;

import com.projectmanager.entities.Cronograma;


public interface CronogramaService {
    public Iterable<Cronograma> findAll();
    public Cronograma find(int id);
    public Cronograma save(int tarefaId, String titulo, String message, String data);
    public void delete(int id);
    public void deleteCronogramasProjeto(int idProjeto);
    public Collection<Cronograma> getCronogramasProjeto(int idProjeto);
}
