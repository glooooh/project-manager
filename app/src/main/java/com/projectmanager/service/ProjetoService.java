package com.projectmanager.service;

import org.kohsuke.github.GHRepository;

import com.projectmanager.entities.Projeto;

public interface ProjetoService {
    public Iterable<Projeto> findAll();
    public Projeto find(int id);
    public Projeto save(GHRepository repo);
    public void delete(int id);
    public boolean exist(int id);
}
