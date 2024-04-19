package com.projectmanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import java.util.Collection;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.projectmanager.entities.Comentario;
import com.projectmanager.entities.Tarefa;
import com.projectmanager.model.ComentarioModel;
import com.projectmanager.model.RepositoryModel;
import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.TarefaService;
import com.projectmanager.service.TarefaServiceImpl;


@Controller
@RequestMapping("/user/{user_id}/repositories/{repo_name}/tarefas/")
public class TarefaController {

    private GithubAPIService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService; 

    @Autowired
	TarefaService tarefaService;
    
    public TarefaController(GithubAPIService githubService,
            OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
    }

    @GetMapping("")
    public String getUserTarefas(Model model){
        return "error";
    }

    @GetMapping("/{tarefa_id}")
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
    @RequestParam("collaborator") List<String> collaborators,@PathVariable("repo_name") String repoName,
    @PathVariable("user_id") String userId) {

        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken);
            
        System.out.println("Entrou no post");
        System.out.println(repoName);
        System.out.println(data);
        System.out.println(nome);
        System.out.println(descricao);
        System.out.println(collaborators);

        try {
            GHRepository repo = githubService.getRepository(loggedUser,repoName);
            Tarefa tarefa = new Tarefa();
		    tarefa.setPrazo(data);
            tarefa.setTitulo(nome);
            tarefa.setDescricao(descricao);
            for (int i = 0; i < collaborators.size(); i++) {
		        tarefaService.save(tarefa, githubService.getCollaboratorId(collaborators.get(i),repo).intValue());
            }
            System.out.println("Feito!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String redirect = "redirect:/user/"+userId+"/repositories/"+repoName+"/tarefas/";
        return redirect;  
    }

    @GetMapping("/{tarefa_id}/comentarios")
    public String getComentarios(Model model,@PathVariable("tarefa_id") String tarefaId){
        //Carregar comentarios ja escritos
        //Transformar o Comentario em Comentario model
        //Por que? Para passar pro thymeleaf com o nome do escritor em vez de o ID
        Collection<ComentarioModel> comentarios = new ArrayList<ComentarioModel>();

        //teste
        ComentarioModel cm1 = new ComentarioModel();
        cm1.setComentario("Nossa galera muito legal isso aqui");
        cm1.setEscritor("Marcos1234");
        ComentarioModel cm2 = new ComentarioModel();
        cm2.setComentario("É verdade muito massa");
        cm2.setEscritor("Davizaoaoao");
        comentarios.add(cm1);
        comentarios.add(cm2);
        //

        model.addAttribute("comentarios",comentarios);

        return "comentarios";
    }

    @PostMapping("/{tarefa_id}/comentarios")
    public String createComentario(@PathVariable("repo_name") String repoName,@PathVariable("user_id") String userId,
    @PathVariable("tarefa_id") String tarefaId){
        //Criar comentario dentro da tarefa

        String redirect = "redirect:/user/"+userId+"/repositories/"+repoName+"/tarefas/"+tarefaId+"/comentarios";
        return redirect;
    }
}
