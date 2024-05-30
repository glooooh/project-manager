package com.projectmanager.service;

import java.io.IOException;
import java.util.List;

import org.kohsuke.github.GHUser;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.projectmanager.model.RepositoryModel;
import com.projectmanager.model.UsuarioModel;

public interface GitService {
    UsuarioModel getUser(String accessToken);
    RepositoryModel getRepository(String owner, String repoName);
    List<RepositoryModel> getRepositories(String owner);
    Long getCollaboratorId(String collaboratorName, RepositoryModel repo);

    void saveIssuesAsTarefas(RepositoryModel repo,TarefaService tarefaService);
    String getAccessToken(OAuth2AuthenticationToken authenticationToken, String clientRegistrationId,OAuth2AuthorizedClientService oauth2AuthorizedClientService);
    String getUserId(OAuth2AuthenticationToken authenticationToken, OAuth2AuthorizedClientService oauth2AuthorizedClientService) ;
    boolean isAuthenticated(OAuth2AuthenticationToken authenticationToken);
    boolean validateUser(UsuarioModel loggedUser, String userId);
}