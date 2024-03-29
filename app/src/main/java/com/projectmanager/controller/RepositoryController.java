package com.projectmanager.controller;

import java.io.IOException;

import org.kohsuke.github.GHMyself;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.projectmanager.model.RepositoryModel;
import com.projectmanager.service.GithubAPIService;

@Controller
@RequestMapping("/user/{user_id}/repository/")
public class RepositoryController {
    
    private GithubAPIService githubService;
    private final OAuth2AuthorizedClientService oauth2AuthorizedClientService;
    

    public RepositoryController(GithubAPIService githubService,
            OAuth2AuthorizedClientService oauth2AuthorizedClientService) {
        this.githubService = githubService;
        this.oauth2AuthorizedClientService = oauth2AuthorizedClientService;
    }

    //Página principal do repositório
    @GetMapping("/{repo_name}")
    public String getRepository(@PathVariable("user_id")String userId,@PathVariable("repo_name") String repoName, OAuth2AuthenticationToken authenticationToken) {
        
        String accessToken = githubService.getAccessToken(authenticationToken,"github",oauth2AuthorizedClientService);
        GHMyself user = githubService.getUser(accessToken); //Objeto do usuario

        if(!userId.equals(Long.toString(user.getId()))){
            return "error"; //Html de erro
        }
        try {
            RepositoryModel repo = githubService.getRepositoryModel(user,repoName);//Objeto do repositorio

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "repository"; //Criar esse html
    }
}
