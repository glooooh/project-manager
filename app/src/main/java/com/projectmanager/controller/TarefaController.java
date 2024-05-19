package com.projectmanager.controller;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;

import java.util.Collection;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectmanager.entities.Comentario;
import com.projectmanager.entities.Tarefa;
import com.projectmanager.exceptions.BusinessException;
import com.projectmanager.forms.TarefaForm;
import com.projectmanager.model.ComentarioModel;
import com.projectmanager.model.RepositoryModel;
import com.projectmanager.service.ComentarioService;
import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.TarefaService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user/{user_id}/repositories/{repo_name}/tasks")
public class TarefaController {

    private GithubAPIService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;

    @Autowired
    TarefaService tarefaService;

    @Autowired
    ComentarioService comentarioService;

    public TarefaController(GithubAPIService githubService,
            OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
    }

    @GetMapping("")
    public String getUserTarefas(Model model, @PathVariable("repo_name") String repoName,
    @PathVariable("user_id") String user_id, OAuth2AuthenticationToken authenticationToken,
    @RequestParam(value="error",required = false) String errorMessage) {
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        
        model.addAttribute("user_id", user_id);
        
        model.addAttribute("error", errorMessage);
 
        try {
            GHMyself loggedUser = githubService.getUser(accessToken); // Objeto do usuario
            githubService.validateUser(loggedUser, user_id);
            GHRepository repo = githubService.getRepository(loggedUser, repoName);
            int repoId = (int) repo.getId();
            Collection<Tarefa> tasks = tarefaService.getTaskByProject(repoId);
            model.addAttribute("tarefas", tasks);

            RepositoryModel repository = githubService.getRepositoryModel(loggedUser, repoName);// Objeto do repositório
            model.addAttribute("repository", repository);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "tarefas";
    }
    @GetMapping("/{tarefa_id}")
    public String getTarefa(Model model) {
        return "error";
    }

    @PostMapping("/new")
    public String createTarefa(OAuth2AuthenticationToken authenticationToken, @ModelAttribute TarefaForm novaTarefa,
                            @PathVariable("repo_name") String repoName,@PathVariable("user_id") String user_id,
                            Model model) {

        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        try {       
            tarefaService.save(novaTarefa, repoName, accessToken, user_id);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error",e.getMessage());
            return "error";
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            model.addAttribute("error",e.getMessage());
            return "error";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            model.addAttribute("error",e.getMessage());
            return "error";
        } catch (BusinessException e) {
            String redirect = "redirect:/user/" + user_id + "/repositories/" + repoName + "/tasks?error=" + e.getMessage();
            return redirect;
        } catch (PermissionDeniedDataAccessException e){
            model.addAttribute("error",e.getMessage());
            return "error";
        }
        
        String redirect = "redirect:/user/" + user_id + "/repositories/" + repoName + "/tasks";
        return redirect;
    }

    @GetMapping("/{tarefa_id}/comentarios")
    public String getComentarios(Model model, @PathVariable("tarefa_id") String tarefaId) {

        Collection<ComentarioModel> comentarios = comentarioService.getComentarioTarefa(Integer.parseInt(tarefaId));
        model.addAttribute("comentarios", comentarios);

        return "comentarios";
    }

    @PostMapping("/{tarefa_id}/comentarios")
    public String createComentario(@PathVariable("repo_name") String repoName, @PathVariable("user_id") String userId,
            @PathVariable("tarefa_id") String tarefaIdStr,@RequestParam String message) {
        // Criar comentario dentro da tarefa
        System.out.println("Estado comentario");

        comentarioService.save(Integer.parseInt(tarefaIdStr), userId, message);

        System.out.println(message);


        String redirect = "redirect:/user/" + userId + "/repositories/" + repoName + "/tasks/" + tarefaIdStr
                + "/comentarios";
        return redirect;
    }

    @GetMapping("/{tarefa_id}/edit")
    public String editTarefa(Model model, @PathVariable("tarefa_id") String tarefaId) {
        Tarefa tarefa = tarefaService.find(Integer.parseInt(tarefaId));
        model.addAttribute("tarefa", tarefa);
        return "edittarefa";
    }

    @PostMapping("/{tarefa_id}/edit")
    public String postMethodName(OAuth2AuthenticationToken authenticationToken, @ModelAttribute TarefaForm novaTarefa,
    @PathVariable("repo_name") String repoName, @PathVariable("tarefa_id") String tarefaId, @PathVariable("user_id") String user_id) throws IOException {
        
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        try {
            int id = Integer.parseInt(tarefaId);

            GHMyself loggedUser = githubService.getUser(accessToken); // Objeto do usuario
            githubService.validateUser(loggedUser, user_id);
            GHRepository repo = githubService.getRepository(loggedUser, repoName);

            tarefaService.edit(novaTarefa, id, repo);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        
        return "redirect:/user/{user_id}/repositories/{repo_name}/tasks";
    }
    
}