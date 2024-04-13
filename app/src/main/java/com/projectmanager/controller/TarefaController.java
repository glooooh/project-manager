package com.projectmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectmanager.entities.Tarefa;
import com.projectmanager.service.TarefaService;
import com.projectmanager.service.TarefaServiceImpl;


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
    @GetMapping("/new")
    public String createTarefa(){
        return "newtarefa";
    }
    @PostMapping("/new")
    public String createTarefa(@RequestParam("data") String data,@RequestParam("titulo") String nome,
    @RequestParam("descricao") String descricao) {
    
        System.out.println("Entrou no post");
        System.out.println(data);
        System.out.println(nome);
        System.out.println(descricao);
        TarefaService tarefaService =  new TarefaServiceImpl();

        return "redirect:/";

       // Tarefa createdTarefa=tarefaService.save(newTarefa);
        // Code to create a new Tarefa for the specified user and repository
        // This is just a placeholder, replace with your actual service call
        //Tarefa createdTarefa = tarefaService.createTarefa(userId, repoId, newTarefa);
        
        // Return the created Tarefa in the response
       // return new ResponseEntity<>("Created tarefa", HttpStatus.CREATED);
        
    }

}
