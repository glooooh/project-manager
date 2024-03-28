package com.projectmanager.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.projectmanager.model.RepositoryModel;
import com.projectmanager.service.GithubAPIService;
import com.projectmanager.service.GithubWebService;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/github")
public class GithubController {
    
    private final GithubWebService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;
    private final GithubAPIService apiService;

    @Autowired
    public GithubController(GithubWebService githubService, OAuth2AuthorizedClientService oauth2AuthorizedClientService, GithubAPIService apiService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
        this.apiService = apiService;
    }
    
    @GetMapping("/teste")
    public String getGithub(OAuth2AuthenticationToken authenticationToken){
        String accessToken = getAccessToken(authenticationToken, "github");
        GHMyself user = apiService.getUser(accessToken);
        

        try {
            apiService.getRepositories(user);
            System.out.println(accessToken);
            System.out.println("oi");
            for(int i = 0; i < user.listRepositories().toList().size();i++){
                System.out.println(user.listRepositories().toList().get(i).getName());
            }
            
            return user.listRepositories().toList().toString();
            
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        } 
        
    }

    //Pagina que lista repositorios
    @GetMapping("/repo")
    public Mono<List<String>> getRepo(OAuth2AuthenticationToken authenticationToken){
        System.out.println("/n/n/naccessToken");
        String accessToken = getAccessToken(authenticationToken, "github");
        Mono<List<String>> repositories = githubService.getRepositories(accessToken);
        System.out.println(accessToken);
        System.out.println(repositories);
        return repositories;
    }

    @GetMapping("/lista")
    public String listagemRepo(Model model, OAuth2AuthenticationToken authenticationToken){
        RepositoryModel repos = new RepositoryModel();
        String accessToken = getAccessToken(authenticationToken, "github");
        GHMyself user = apiService.getUser(accessToken);
        try {
            GHRepository teste = user.getRepository("CRUD_POKEMON");
            repos.setId(teste.getId());
            repos.setName(teste.getName());
            model.addAttribute("repos",repos);
        } catch (Exception e) {
            return null;
        }         
        //String accessToken = getAccessToken(authenticationToken, "github");
        //GHMyself user=apiService.getUser(accessToken);
        return "repos";
    }
  
    private String getAccessToken(OAuth2AuthenticationToken authenticationToken, String clientRegistrationId){
        OAuth2AuthorizedClient client = oauth2AuthorizedClientService.loadAuthorizedClient(clientRegistrationId, authenticationToken.getName());

        if (client != null) {
            OAuth2AccessToken accessToken = client.getAccessToken();
            if (accessToken != null) {
                return accessToken.getTokenValue();
            } else {
                System.out.println("Access token is missing for client: " + clientRegistrationId);
                throw new RuntimeException("Access token is missing for client"); 
            } 
        }
        else {
            // Log or handle missing client case
            System.out.println("Client not found for clientRegistrationId: " + clientRegistrationId);
            throw new RuntimeException("Client missing"); 
        }
    
    }
}
