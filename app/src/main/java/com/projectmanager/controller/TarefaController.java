package com.projectmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projectmanager.entities.Tarefa;
import com.projectmanager.service.TarefaService;


@Controller
@RequestMapping("/user/{user_id}/repositories/{repo_id}/tarefas/")
public class TarefaController {

    @GetMapping("")
    public String getUserRepositoriesTarefas(){
        return "error";
    }

    @GetMapping("/<tarefa_id>")
    public String getTarefa(){
        return "error";
    }
    @GetMapping("/create")
    public String createTarefa(){
        return "error";
    }
    @PostMapping("/create")
    public ResponseEntity<Tarefa> createTarefa(@PathVariable("user_id") String userId, @PathVariable("repo_id") String repoId, @RequestBody Tarefa newTarefa) {
        TarefaService tarefaService;
        // Code to create a new Tarefa for the specified user and repository
        // This is just a placeholder, replace with your actual service call
        //Tarefa createdTarefa = tarefaService.createTarefa(userId, repoId, newTarefa);
        
        // Return the created Tarefa in the response
        return new ResponseEntity<>(createdTarefa, HttpStatus.CREATED);
        
    }

}
