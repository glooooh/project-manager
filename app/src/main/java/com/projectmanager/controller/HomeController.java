package com.projectmanager.controller;

import java.io.IOException;

import java.util.Collection;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.projectmanager.model.UserModel;
import com.projectmanager.service.GithubAPIService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private GithubAPIService githubService; // Injete o serviço que obtém os repositórios do GitHub

    @Autowired
    private OAuth2AuthorizedClientService oauth2AuthorizedClientService; // Injete o serviço de cliente autorizado
                                                                         // OAuth2

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

    @GetMapping("/contato")
    public String getContato() {
        return "contato";
    }

    @GetMapping("/sobre")
    public String getSobre() {
        return "sobre";
    }

    private boolean isAuthenticated(OAuth2AuthenticationToken authenticationToken) {
        return authenticationToken != null && authenticationToken.isAuthenticated();
    }

    private String processAuthenticatedUser(Model model, OAuth2AuthenticationToken authenticationToken)
            throws IOException {
        String accessToken = githubService.getAccessToken(authenticationToken, "github", oauth2AuthorizedClientService);
        GHMyself loggedUser = githubService.getUser(accessToken);
        // Collection<GHRepository> repositories;

        // try {
        //     repositories = githubService.getRepositories(loggedUser);
        //     model.addAttribute("repositories", repositories);
        // } catch (IOException e) {
        //     e.printStackTrace(); // Trate de forma adequada a exceção
        // }

        UserModel user = new UserModel(loggedUser.getLogin(), loggedUser.getId(), "dummyToken",
                loggedUser.getEmail(), "dummyFirstName");
        model.addAttribute("user", user);

        String user_id = String.valueOf(loggedUser.getId());
        return "redirect:/user/" + user_id;
    }

}
