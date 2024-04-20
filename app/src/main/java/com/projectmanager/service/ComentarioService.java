package com.projectmanager.service;

import java.util.Collection;

import com.projectmanager.entities.Comentario;

public interface ComentarioService {
    public Iterable<Comentario> findAll();
    public Comentario find(int id);
    public Comentario save(Comentario comentario);
    public void delete(int id);
    public Collection<Comentario> getComentarioTarefa(int tarefaId);
}
