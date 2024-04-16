package com.projectmanager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Colaborador;
import com.projectmanager.entities.Tarefa;
import com.projectmanager.repositories.TarefaRepository;

@Service("TarefaService")
public class TarefaServiceImpl implements TarefaService{

    @Autowired
    TarefaRepository tarefaRepository;
    @Autowired
    ColaboradorService colaboradorService;

    @Override
    public Iterable<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    @Override
    public Tarefa find(int id) {
        return tarefaRepository.findById(id).get();
    }

    @Override
    public Tarefa save(Tarefa tarefa, int usuarioid) {
        Tarefa newTarefa = tarefaRepository.save(tarefa);

        Colaborador colaborador = new Colaborador();
        colaborador.setTarefa_id(newTarefa.getId());
        colaborador.setUsuario_id(usuarioid);
        colaboradorService.save(colaborador);
        
        return newTarefa;
    }

    @Override
    public void delete(int id) {
        tarefaRepository.deleteById(id);
    }
}
