package com.projectmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Tarefa;
import com.projectmanager.repositories.TarefaRepository;

@Service("TarefaService")
public class TarefaServiceImpl implements TarefaService{

    @Autowired
    TarefaRepository tarefaRepository;

    @Override
    public Iterable<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    @Override
    public Tarefa find(int id) {
        return tarefaRepository.findById(id).get();
    }

    @Override
    public Tarefa save(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    @Override
    public void delete(int id) {
        tarefaRepository.deleteById(id);
    }

}
