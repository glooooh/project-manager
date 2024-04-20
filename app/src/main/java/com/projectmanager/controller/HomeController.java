package com.projectmanager.controller;

import java.io.IOException;

import org.kohsuke.github.GHMyself;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projectmanager.entities.Usuario;

import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.UsuarioService;

@Controller
public class HomeController {

    @Autowired
    private GithubAPIService githubService; // Injete o serviço que obtém os repositórios do GitHub

    @Autowired
    private OAuth2AuthorizedClientService oauth2AuthorizedClientService; // Injete o serviço de cliente autorizado
                                                                         // OAuth2

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String getIndex(OAuth2AuthenticationToken authenticationToken) {
        if (isAuthenticated(authenticationToken)) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model, OAuth2AuthenticationToken authenticationToken) throws IOException {
        if (!isAuthenticated(authenticationToken)) {
            return "redirect:/";
        }
        return processAuthenticatedUser(model, authenticationToken);
    }

    @GetMapping("/projects")
    public String getProjects(Model model, OAuth2AuthenticationToken authenticationToken) {
        if (!isAuthenticated(authenticationToken)) {
            return "redirect:/";
        }

        // TODO: alterar a rota

        return "redirect:/user/" + getUserId(authenticationToken) + "/projects";
    }

    @GetMapping("/repositories")
    public String getRepositories(Model model, OAuth2AuthenticationToken authenticationToken) {
        if (!isAuthenticated(authenticationToken)) {
            return "redirect:/";
        }

        return "redirect:/user/" + getUserId(authenticationToken) + "/repositories";
    }

    @GetMapping("/sobre")
    public String getSobre(Model model, OAuth2AuthenticationToken authenticationToken) {
        if (isAuthenticated(authenticationToken)) {
            // Se o usuário estiver autenticado, obtenha suas informações e adicione ao
            // modelo
            String accessToken = githubService.getAccessToken(authenticationToken, "github",
                    oauth2AuthorizedClientService);
            GHMyself loggedUser = githubService.getUser(accessToken);
            model.addAttribute("user", loggedUser);
        }

        return "sobre";
    }

    private boolean isAuthenticated(OAuth2AuthenticationToken authenticationToken) {
        return authenticationToken != null && authenticationToken.isAuthenticated();
    }

    private String getUserId(OAuth2AuthenticationToken authenticationToken) {
        if (isAuthenticated(authenticationToken)) {
            try {
                String accessToken = githubService.getAccessToken(authenticationToken, "github",
                        oauth2AuthorizedClientService);
                GHMyself loggedUser = githubService.getUser(accessToken);
                return String.valueOf(loggedUser.getId());
            } catch (Exception e) {
                e.printStackTrace(); // Apenas para fins de depuração, substitua por um tratamento adequado
            }
        }
        return null; // Retornar null ou algum valor padrão se o usuário não estiver autenticado
    }

    private String processAuthenticatedUser(Model model, OAuth2AuthenticationToken authenticationToken)
            throws IOException {
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken);

        long user_id = loggedUser.getId();

        if (!usuarioService.exist((int) user_id)) {
            Usuario usuario = new Usuario();
            usuario.setId((int) user_id);
            usuario.setName(loggedUser.getName());
            usuario.setUsername(loggedUser.getLogin());
            usuarioService.save(usuario);
        }

        return "redirect:/user/" + user_id;
    }
}