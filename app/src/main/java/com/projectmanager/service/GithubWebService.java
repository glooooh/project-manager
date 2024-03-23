package com.projectmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.projectmanager.model.GithubRepoResponse;

import reactor.core.publisher.Mono;

@Service

public class GithubWebService {
    
    private final WebClient webClient;

    @Autowired
    public GithubWebService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.github.com").build();
    }

    public Mono<List<String>> getRepositories(String accessToken){
        return webClient.get()
        .uri("/user/repos")
        .header("Authorization","Bearer "+accessToken)
        .retrieve()
        .bodyToFlux(GithubRepoResponse.class)
        .map(GithubRepoResponse::getName)
        .collectList();
    }

    
}
