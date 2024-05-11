package com.projectmanager.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHPersonSet;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
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
    @Autowired
    ComentarioService comentarioService;

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

        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        Tarefa newTarefa = new Tarefa();
        newTarefa.setTitulo(tarefaForm.getTitulo());
        newTarefa.setDescricao(tarefaForm.getDescricao());
        newTarefa.setPrazo(tarefaForm.getPrazo());
        newTarefa.setId_criador(usuarioid);
        newTarefa.setId_projeto((int) repo.getId());
        newTarefa.setData_criacao(formatador.format(data));
        //newTarefa.setStatus(tarefaForm.getStatus());

        tarefaRepository.save(newTarefa);
        
        colaborador.setTarefa_id(newTarefa.getId());
        
        try {
            //int repoId = (int) repo.getId(); // Obtendo o ID do repositório

            for (GHUser user : repo.getCollaborators()) {
                if(tarefaForm.getCollaborators().contains(user.getLogin())){
                    colaborador.setUsuario_id((int) user.getId());
                    colaboradorService.save(colaborador);
                };
            }
        } catch (IOException e) {
            return newTarefa;
        }
        
        return newTarefa;
    }
    
    @Override
    public Tarefa save(GHIssue issue, GHRepository repo) throws IOException{
        Tarefa newTarefa = new Tarefa();
        newTarefa.setTitulo(issue.getTitle());
        newTarefa.setDescricao(issue.getBody());
        newTarefa.setId_criador((int)issue.getUser().getId());
        newTarefa.setId_projeto((int) repo.getId());
        newTarefa.setData_criacao(issue.getCreatedAt().toString());

        tarefaRepository.save(newTarefa);

        Colaborador novoColaborador = new Colaborador();
        novoColaborador.setTarefa_id(newTarefa.getId());
        
        for (GHUser collaborator : issue.getAssignees()) {   
            novoColaborador.setUsuario_id((int) collaborator.getId());
            colaboradorService.save(novoColaborador);      
        }
    
        return newTarefa;
    }

    @Override
    public void delete(int id) {
        colaboradorService.deleteColaboradoresTarefa(id);
        comentarioService.deleteComentariosTarefa(id);

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

    @Override
    public Tarefa edit(TarefaForm tarefaForm, int tarefaId) {
        Tarefa newTarefa = find(tarefaId);

        newTarefa.setTitulo(tarefaForm.getTitulo());
        newTarefa.setDescricao(tarefaForm.getDescricao());
        newTarefa.setPrazo(tarefaForm.getPrazo());
        //newTarefa.setStatus(tarefaForm.getStatus());

        delete(tarefaId);
        return tarefaRepository.save(newTarefa);
    }

    
}
