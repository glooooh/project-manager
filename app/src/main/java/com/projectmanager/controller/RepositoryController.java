package com.projectmanager.controller;

import java.io.IOException;
import java.util.Collection;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model; // Importe a classe Model

import com.projectmanager.model.RepositoryModel;
import com.projectmanager.model.UsuarioModel;
import com.projectmanager.service.GithubAPIService;

@Controller
@RequestMapping("/user/{user_id}/repositories")
public class RepositoryController {

    private GithubAPIService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;

    public RepositoryController(GithubAPIService githubService,
            OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
    }

    @GetMapping("")
    public String getUserRepositories(@PathVariable("user_id") String user_id, OAuth2AuthenticationToken authenticationToken, Model model) {
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself user = githubService.getUser(accessToken);

        if (!user_id.equals(Long.toString(user.getId()))) {
            model.addAttribute("errorMessage", "Usuário inválido.");
            return "error";
        }

        GHMyself loggedUser = githubService.getUser(accessToken);
        Collection<GHRepository> repositories;
        
        try {
            repositories = githubService.getRepositories(loggedUser);
            model.addAttribute("repositories", repositories);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Erro ao obter os repositórios do usuário: " + e.getMessage());
            return "error";
        }

        return "repositories";
    }

    // Página principal do repositório
    @GetMapping("/{repo_name}")
    public String getRepository(@PathVariable("user_id") String user_id, @PathVariable("repo_name") String repoName,
            OAuth2AuthenticationToken authenticationToken, Model model) {

        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken); // Objeto do usuario

        if (!user_id.equals(Long.toString(loggedUser.getId()))) {
            model.addAttribute("errorMessage", "Você está tentando acessar um repositório de outro usuário");
            return "error";
        }
        try {
            UsuarioModel user = new UsuarioModel(loggedUser.getLogin(), loggedUser.getId(), "dummyToken",
                    loggedUser.getEmail(), "dummyFirstName");
            model.addAttribute("user", user);

            RepositoryModel repo = githubService.getRepositoryModel(loggedUser, repoName);// Objeto do repositório
            GHRepository repo2 = loggedUser.getRepository(repoName);
            
            model.addAttribute("repository", repo);

            System.out.println("hey");

        } catch (IOException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }

        return "repos";
    }
}