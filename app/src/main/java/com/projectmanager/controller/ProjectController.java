package com.projectmanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.projectmanager.model.RepositoryModel;
import com.projectmanager.model.UsuarioModel;
import com.projectmanager.model.ProjetoModel;
import com.projectmanager.repositories.ProjetoRepository;
import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.ProjetoService;

@Controller
@RequestMapping("/user/{user_id}/projects")
public class ProjectController {

    private final GithubAPIService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;
    
    @Autowired
	ProjetoService projetoService;

    @Autowired
    public ProjectController(GithubAPIService githubService,
            OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
    }

    @GetMapping("")
    public String getUserRepositories(@PathVariable("user_id") String userId,
                                      OAuth2AuthenticationToken authenticationToken,
                                      Model model) {
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself user = githubService.getUser(accessToken);
        
        Collection<Projeto> projects = (Collection<Projeto>) projetoService.findAll();

        model.addAttribute("projetos", projects);

        return "projects"; // Supondo que "projects" seja o nome da sua página de projetos
    }

    @GetMapping("/{project_id}")
    public String getRepository(@PathVariable("user_id") String user_id, @PathVariable("project_id") int projectId,
            OAuth2AuthenticationToken authenticationToken, Model model) {

        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken); // Objeto do usuario

        if (!user_id.equals(Long.toString(loggedUser.getId()))) {
            model.addAttribute("errorMessage", "Você está tentando acessar um projeto de outro usuário");
            return "error";
        }

        Projeto projeto = projetoService.find(projectId);

        model.addAttribute("usuario", loggedUser);
        model.addAttribute("projeto", projeto);

        return "project";
    }
}