package com.projectmanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHCompare.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.projectmanager.entities.Projeto;
import com.projectmanager.entities.Tarefa;
import com.projectmanager.service.ColaboradorService;
import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.ProjetoService;
import com.projectmanager.service.TarefaService;

@Controller
public class UserController {

    private final GithubAPIService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    ProjetoService projetoService;

    @Autowired
    TarefaService tarefaService;

    @Autowired
    public UserController(GithubAPIService githubService,
            OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
    }

    @GetMapping("/user/{user_id}")
    public String getUserPage(@PathVariable("user_id") String userId, OAuth2AuthenticationToken authenticationToken,
            Model model) {
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        int userIdInt = Integer.parseInt(userId);
        try {
            Iterable<Tarefa> tasks = colaboradorService.findTasksByIDUser(userIdInt, tarefaService);
            model.addAttribute("tarefas", tasks);

            GHMyself loggedUser = githubService.getUser(accessToken);
            Collection<GHRepository> repositories = githubService.getRepositories(loggedUser);

            Collection<GHRepository> projects = (Collection<GHRepository>) projetoService.findTop3ByOrderByDataCriacaoDesc(repositories);

            model.addAttribute("repositories", projects);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ocorreu um erro ao recuperar as tarefas do usuário. " + e.getMessage());
            return "error";
        }

        return "user";
    }
}
