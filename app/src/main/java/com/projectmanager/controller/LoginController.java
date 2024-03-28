package com.projectmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.projectmanager.service.GithubWebService;

import reactor.core.publisher.Mono;

@Controller
public class LoginController {

    @Autowired
    private GithubWebService githubWebService; // Injete o serviço que obtém os repositórios do GitHub

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
            Mono<List<String>> repositories = githubWebService.getRepositories(accessToken);

            // Adicionar os repositórios à model
            model.addAttribute("repositories", repositories.block()); // Observe que estamos bloqueando a chamada aqui

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
