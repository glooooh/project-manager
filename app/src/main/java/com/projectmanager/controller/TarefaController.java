package com.projectmanager.controller;

import java.io.IOException;
import java.util.List;

import java.util.Collection;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.projectmanager.forms.TarefaForm;
import com.projectmanager.model.RepositoryModel;
import com.projectmanager.model.TarefaModel;
import com.projectmanager.service.ComentarioService;
import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.TarefaService;

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
    @PathVariable("user_id") String user_id, OAuth2AuthenticationToken authenticationToken) {
        System.out.println("tarefas");
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken);
        GHRepository repo;
        try {
            repo = githubService.getRepository(loggedUser, repoName);
            int repoId = (int) repo.getId();

            Collection<Tarefa> tasks = tarefaService.getTaskByProject(repoId);

            model.addAttribute("repository", repo);
            model.addAttribute("tarefas", tasks);
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }

        return "tarefas";
    }
    @GetMapping("/{tarefa_id}")
    public String getTarefa(Model model) {
        return "error";
    }

    // @GetMapping("/new")
    // public String createTarefa(Model model, OAuth2AuthenticationToken authenticationToken,
    //         @PathVariable("repo_name") String repoName) {
    //     String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
    //     GHMyself loggedUser = githubService.getUser(accessToken);
    //     try {
    //         RepositoryModel repo = githubService.getRepositoryModel(loggedUser, repoName);
    //         model.addAttribute("repo", repo);

    //     } catch (IOException e) {
    //         return "error";
    //     }

    //     return "newtarefa";
    // }

    @PostMapping("/new")
    public String createTarefa(OAuth2AuthenticationToken authenticationToken, @ModelAttribute TarefaForm novaTarefa,
     @PathVariable("repo_name") String repoName,@PathVariable("user_id") String userId) {

        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken);
        System.out.println(novaTarefa.toString());

        // TESTE
        
        /*        
         try {
            GHRepository repo = githubService.getRepository(loggedUser, repoName);
            int repoId = (int) repo.getId(); // Obtendo o ID do repositório
            Tarefa tarefa = new Tarefa();
            tarefa.setPrazo(data);
            tarefa.setTitulo(nome);
            tarefa.setDescricao(descricao);
            tarefa.setId_projeto(repoId);
            for (int i = 0; i < collaborators.size(); i++) {
                tarefaService.save(tarefa, githubService.getCollaboratorId(collaborators.get(i), repo).intValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        String redirect = "redirect:/user/" + userId + "/repositories/" + repoName + "/tasks";
        return redirect;
    }

    @GetMapping("/{tarefa_id}/comentarios")
    public String getComentarios(Model model, @PathVariable("tarefa_id") String tarefaId) {
        // Carregar comentarios ja escritos
        // Transformar o Comentario em Comentario model
        // Por que? Para passar pro thymeleaf com o nome do escritor em vez de o ID
        Collection<Comentario> comentarios = comentarioService.getComentarioTarefa(Integer.parseInt(tarefaId));

        // teste
        /*
         * ComentarioModel cm1 = new ComentarioModel();
         * cm1.setComentario("Nossa galera muito legal isso aqui");
         * cm1.setEscritor("Marcos1234");
         * ComentarioModel cm2 = new ComentarioModel();
         * cm2.setComentario("É verdade muito massa");
         * cm2.setEscritor("Davizaoaoao");
         * comentarios.add(cm1);
         * comentarios.add(cm2);
         */
        //

        model.addAttribute("comentarios", comentarios);

        return "comentarios";
    }

    @PostMapping("/{tarefa_id}/comentarios")
    public String createComentario(@PathVariable("repo_name") String repoName, @PathVariable("user_id") String userId,
            @PathVariable("tarefa_id") String tarefaIdStr,@RequestParam String message) {
        // Criar comentario dentro da tarefa
        System.out.println("Estado comentario");
        int tarefaId = Integer.parseInt(tarefaIdStr);
        Comentario comentario = new Comentario();
        comentario.setTarefa(tarefaId);
        comentario.setEscritor(userId);
        comentario.setComentario(message);

        comentarioService.save(comentario);

        System.out.println(message);


        String redirect = "redirect:/user/" + userId + "/repositories/" + repoName + "/tasks/" + tarefaId
                + "/comentarios";
        return redirect;
    }
}