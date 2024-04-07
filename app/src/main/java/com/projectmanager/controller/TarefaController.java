package com.projectmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
    
}
