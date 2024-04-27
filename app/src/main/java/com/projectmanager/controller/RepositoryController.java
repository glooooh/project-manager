package com.projectmanager.controller;

import java.io.IOException;
import java.util.Collection;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model; // Importe a classe Model

import com.projectmanager.entities.Projeto;
import com.projectmanager.entities.Tarefa;
import com.projectmanager.model.RepositoryModel;
import com.projectmanager.model.UsuarioModel;
import com.projectmanager.service.ColaboradorService;
import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.ProjetoService;
import com.projectmanager.service.ProjetoServiceImpl;
import com.projectmanager.service.TarefaService;

@Controller
@RequestMapping("/user/{user_id}/repositories")
public class RepositoryController {

    private GithubAPIService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;

    @Autowired
    ProjetoService projetoService;

    @Autowired
    TarefaService tarefaService;

    @Autowired
    ColaboradorService colaboradorService;

    public RepositoryController(GithubAPIService githubService,
            OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
    }

    @GetMapping("")
    public String getUserRepositories(@PathVariable("user_id") String user_id,
            OAuth2AuthenticationToken authenticationToken, Model model) {
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken);

        //Verifica se o usuário logado está acessando a própria página
        if (!user_id.equals(Long.toString(loggedUser.getId()))) {
            model.addAttribute("errorMessage", "Usuário inválido.");
            return "error";
        }
        // Obter todos os repositórios do usuário no GitHub
        try {
            Collection<GHRepository> repositories = githubService.getRepositories(loggedUser);
            model.addAttribute("repositories", repositories);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Erro ao obter os repositórios do usuário: " + e.getMessage());
            return "error";
        }
        model.addAttribute("objeto_da_lista", "Repositories");
        return "repositories";
    }

    // Página principal do repositório
    @GetMapping("/{repo_name}")
    public String getRepository(@PathVariable("user_id") String user_id, @PathVariable("repo_name") String repoName,
            OAuth2AuthenticationToken authenticationToken, Model model) {

        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken); // Objeto do usuario

        if (!user_id.equals(Long.toString(loggedUser.getId()))) { //TODO Transformar em funcao de validacao?
            model.addAttribute("errorMessage", "Você está tentando acessar um repositório de outro usuário");
            return "error";
        }
        try {
            processRepository(loggedUser, repoName); //TODO Transformar em função do repositoryService
            UsuarioModel user = new UsuarioModel(loggedUser.getLogin(), loggedUser.getId(), "dummyToken",
                    loggedUser.getEmail(), "dummyFirstName");
            model.addAttribute("user", user);

            RepositoryModel repo = githubService.getRepositoryModel(loggedUser, repoName);// Objeto do repositório

            model.addAttribute("repository", repo);

        } catch (IOException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }

        return "project";
    }

    private void processRepository(GHMyself loggedUser, String repoName) throws IOException {

        GHRepository repo = githubService.getRepository(loggedUser, repoName);

        projetoService.save(repo);
    }
}