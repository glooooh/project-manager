package com.projectmanager.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectmanager.entities.Tarefa;
import com.projectmanager.model.RepositoryModel;
import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.TarefaService;
import com.projectmanager.service.TarefaServiceImpl;


@Controller
@RequestMapping("/user/{user_id}/repositories/{repo_name}/tarefas/")
public class TarefaController {

    private GithubAPIService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService; 
    
    public TarefaController(GithubAPIService githubService,
            OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
    }

    @GetMapping("")
    public String getUserTarefas(Model model){
        return "error";
    }

    @GetMapping("/<tarefa_id>")
    public String getTarefa(Model model){
        return "error";
    }
    @GetMapping("/new")
    public String createTarefa(Model model,OAuth2AuthenticationToken authenticationToken, @PathVariable("repo_name") String repoName){
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken);
        try {
            RepositoryModel repo = githubService.getRepositoryModel(loggedUser, repoName);
            model.addAttribute("repo", repo);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "newtarefa";
    }
    @PostMapping("/new")
    public String createTarefa(OAuth2AuthenticationToken authenticationToken,@RequestParam("data") String data,
    @RequestParam("titulo") String nome,@RequestParam("descricao") String descricao,
    @RequestParam("collaborator") List<String> collaborators,@PathVariable("repo_name") String repoName) {
        
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken);
        
        //TODO usar o username dos colaboradores ou transformar o username em id?
        System.out.println("Entrou no post");
        System.out.println(data);
        System.out.println(nome);
        System.out.println(descricao);
        System.out.println(collaborators);

        try {
            GHRepository repo = loggedUser.getRepository(repoName);
            //for pessoa in collaborator
            //githubService.getCollaboratorId(pessoa,repo)
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
