package com.projectmanager.service;

import com.projectmanager.entities.Colaborador;
import com.projectmanager.entities.Tarefa;

public interface ColaboradorService {
    public Iterable<Colaborador> findAll();
    public Colaborador save(Colaborador colaborador);
    public Iterable<Tarefa> findProjectsByID(int id);
}