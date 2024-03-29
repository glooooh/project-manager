package com.projectmanager.controller;

import java.io.IOException;

import java.util.Collection;


import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.projectmanager.service.GithubAPIService;



@Controller
public class HomeController {

    @Autowired
    private GithubAPIService githubService; // Injete o serviço que obtém os repositórios do GitHub

    @Autowired
    private OAuth2AuthorizedClientService oauth2AuthorizedClientService; // Injete o serviço de cliente autorizado
                                                                         // OAuth2

    // Descomentar isso e remover o /home dos urls com autenticacao
    /*
     * @GetMapping
     * public String defaultRedirect(){
     * return "redirect:/home";
     * }
     */

    @GetMapping("/home")
    public String getHome(Model model, OAuth2AuthenticationToken authenticationToken) {
        if (!(authenticationToken != null && authenticationToken.isAuthenticated())) {
            return "redirect:/";
        } else {
            String accessToken = getAccessToken(authenticationToken, "github");

            // Obter os repositórios do usuário logado
            GHMyself loggedUser = githubService.getUser(accessToken);
            Collection<GHRepository> repositories;
            
            try {
                repositories = githubService.getRepositories(loggedUser);
                model.addAttribute("repositories", repositories);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Adicionar os repositórios à model
             // Observe que estamos bloqueando a chamada aqui

            return "home";
        }
    }

    @GetMapping("/contato")
    public String getContato() {
        return "contato";
    }

    @GetMapping("/sobre")
    public String getSobre() {
        return "sobre";
    }

    private String getAccessToken(OAuth2AuthenticationToken authenticationToken, String clientRegistrationId) {
        OAuth2AuthorizedClient client = oauth2AuthorizedClientService.loadAuthorizedClient(clientRegistrationId,
                authenticationToken.getName());

        if (client != null) {
            OAuth2AccessToken accessToken = client.getAccessToken();
            if (accessToken != null) {
                return accessToken.getTokenValue();
            } else {
                throw new RuntimeException("Access token is missing for client");
            }
        } else {
            throw new RuntimeException("Client missing");
        }
    }
}
