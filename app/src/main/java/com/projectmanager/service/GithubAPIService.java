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
import com.projectmanager.model.UserModel;

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

    public RepositoryModel getRepositoryModel(GHMyself user, String repoName) throws IOException {
        RepositoryModel newRepo = new RepositoryModel();
        GHRepository oldRepo = user.getRepository(repoName);

        newRepo.setName(oldRepo.getName());
        newRepo.setId(oldRepo.getId());
        newRepo.setDescription(oldRepo.getDescription());
        newRepo.setLanguage(oldRepo.getLanguage());
        newRepo.setOwner(oldRepo.getOwnerName());
        newRepo.setUrl(oldRepo.getUrl().toString());

        return newRepo;
    }

    public UserModel getUserModel(String accessToken) throws IOException {

        GHMyself user = getUser(accessToken);
    UserModel newUser = new UserModel(user.getLogin(), user.getId(), accessToken,user.getEmail(),user.getName());

    // Set other user attributes (optional)
    newUser.setFirstName(user.getName()); // Assuming getName() provides the full name
    // Check if user object has methods for additional attributes like website, company, etc. and set them accordingly.

    return newUser;
}
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
            // Log or handle missing client case
            System.out.println("Client not found for clientRegistrationId: " + clientRegistrationId);
            throw new RuntimeException("Client missing"); 
        }
    
    }
    
}
