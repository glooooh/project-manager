package com.projectmanager.service;

import com.projectmanager.entities.Projeto;

public interface ProjetoService {
    public Iterable<Projeto> findAll();
    public Projeto find(int id);
    public Projeto save(Projeto projeto);
    public void delete(int id);
    public boolean exist(int id);
}
