package com.projectmanager.service;

import java.io.IOException;
import java.util.Collection;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;


import com.projectmanager.model.RepositoryModel;
import com.projectmanager.model.UsuarioModel;

@Service
public class GithubAPIService {

    @Autowired
    public GithubAPIService() {}
    
    public GHMyself getUser(String accessToken) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(accessToken).build();
            GHMyself user = github.getMyself();
            return user;
            } catch (IOException e) {
            System.out.println(e);
            return null;
        }        
    }

    public Collection<GHRepository> getRepositories(GHMyself user) throws IOException {
        Collection<GHRepository> repositories = user.getRepositories().values();
        return repositories;
    }

    //Gera repositoryModel
    public RepositoryModel getRepositoryModel(GHMyself user, String repoName) throws IOException {
        
        Collection<GHRepository> repositories = getRepositories(user);
        RepositoryModel newRepo = new RepositoryModel();

        for (GHRepository oldRepo: repositories){
            if(oldRepo.getName().equals(repoName)){
                newRepo.setName(oldRepo.getName());
                newRepo.setId(oldRepo.getId());
                newRepo.setDescription(oldRepo.getDescription());
                newRepo.setLanguage(oldRepo.getLanguage());
                newRepo.setOwner(oldRepo.getOwnerName());
                newRepo.setUrl(oldRepo.getUrl().toString());
                newRepo.setBranches(oldRepo.getBranches().keySet());
                newRepo.setCollaborators(oldRepo.getCollaboratorNames());
                
            }
            //System.out.println();
        }
        return newRepo;
    }

    public UsuarioModel getUserModel(String accessToken) throws IOException {

        GHMyself user = getUser(accessToken);
        UsuarioModel newUser = new UsuarioModel(user.getLogin(), user.getId(), accessToken,user.getEmail(),user.getName());

    return newUser;
}

    //Retorna o AccessToken do usuário a partir do token de autenticação do oauth
    public String getAccessToken(OAuth2AuthenticationToken authenticationToken, String clientRegistrationId,OAuth2AuthorizedClientService oauth2AuthorizedClientService){
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
            
            System.out.println("Client not found for clientRegistrationId: " + clientRegistrationId);
            throw new RuntimeException("Client missing"); 
        }
    
    }
    
}
