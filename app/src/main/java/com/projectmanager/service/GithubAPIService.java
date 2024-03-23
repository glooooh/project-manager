package com.projectmanager.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    
}
