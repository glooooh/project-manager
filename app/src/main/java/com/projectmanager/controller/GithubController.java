package com.projectmanager.controller;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projectmanager.model.RepositoryModel;
import com.projectmanager.service.GithubAPIService;
//================================================
//ESTE ARQUIVO CONTÉM APENAS PAGINAS DE TESTE 
//================================================
@Controller
@RequestMapping("/github")
public class GithubController {
    
    
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;
    private final GithubAPIService githubService;

    @Autowired
    public GithubController( OAuth2AuthorizedClientService oauth2AuthorizedClientService, GithubAPIService githubService) {
       
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
        this.githubService = githubService;
    }
    
    @GetMapping("/teste")
    public String getGithub(OAuth2AuthenticationToken authenticationToken){
        String accessToken = githubService.getAccessToken(authenticationToken, "github",oauth2AuthorizedClientService);
        GHMyself user = githubService.getUser(accessToken);
        

        try {
            githubService.getRepositories(user);
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
    /* 
    @GetMapping("/repo")
    public Mono<List<String>> getRepo(OAuth2AuthenticationToken authenticationToken){
        System.out.println("/n/n/naccessToken");
        String accessToken = getAccessToken(authenticationToken, "github");
        Mono<List<String>> repositories = githubService.getRepositories(accessToken);
        System.out.println(accessToken);
        System.out.println(repositories);
        return repositories;
    }*/
 
    @GetMapping("/lista")
    public String listagemRepo(Model model, OAuth2AuthenticationToken authenticationToken){
        RepositoryModel repos = new RepositoryModel();
        String accessToken = githubService.getAccessToken(authenticationToken, "github",oauth2AuthorizedClientService);
        GHMyself user = githubService.getUser(accessToken);
        try {
            GHRepository testenome = user.getRepository("CRUD_POKEMON");
            String id = Long.toString(testenome.getId());
            GHRepository testeid = user.getRepository(id);
            System.out.println(testenome);
           // repos.setId(teste.getId());
            //repos.setName(teste.getName());
            //model.addAttribute("repos",repos);
            return testenome.toString();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }         
        //String accessToken = getAccessToken(authenticationToken, "github");
        //GHMyself user=apiService.getUser(accessToken);
        
    }
}
