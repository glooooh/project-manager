package com.projectmanager.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Colaborador;
import com.projectmanager.entities.Tarefa;
import com.projectmanager.forms.TarefaForm;
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
    public Tarefa save(TarefaForm tarefaForm, int usuarioid, GHRepository repo) {
        
        Colaborador colaborador = new Colaborador();

        Tarefa newTarefa = new Tarefa();
        newTarefa.setTitulo(tarefaForm.getTitulo());
        newTarefa.setDescricao(tarefaForm.getDescricao());
        newTarefa.setPrazo(tarefaForm.getPrazo());

        tarefaRepository.save(newTarefa);
        
        colaborador.setTarefa_id(newTarefa.getId());
        
        try {
            int repoId = (int) repo.getId(); // Obtendo o ID do repositório

            for (int i = 0; i < repo.getCollaborators().size(); i++) {
                colaborador.setUsuario_id(usuarioid);
                colaboradorService.save(colaborador);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return newTarefa;
    }

    @Override
    public void delete(int id) {
        tarefaRepository.deleteById(id);
    }

    @Override
    public Collection<Tarefa> getTaskByProject(int projetoid) {
        ArrayList<Tarefa> tarefas = new ArrayList<>();
        
        for(Tarefa tarefa : findAll()){
			if(tarefa.getId_projeto() == projetoid){
                tarefas.add(tarefa);
            }
		}

        return tarefas;
    }
}
