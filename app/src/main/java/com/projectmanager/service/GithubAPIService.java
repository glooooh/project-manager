package com.projectmanager.service;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

public class GithubAPIService {

    
    private GHMyself getUser(String accessToken) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(accessToken).build();
            GHMyself user = github.getMyself();
            return user;
        } catch (Exception e) {
            return null;
        }
       
    }
    

  
}
