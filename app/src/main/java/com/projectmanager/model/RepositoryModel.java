package com.projectmanager.model;

import java.util.Set;

import lombok.Data;

@Data

public class RepositoryModel {
    private long id;
    private String owner;
    private String name;
    private String description;
    private String url;
    private String language;
    private Set<String> branches;
    private Set<String> collaborators;
    private String createdAt;
    
    public RepositoryModel() {
    }

    public RepositoryModel(long id, String owner, String name, String description, String url, String language,
            Set<String> branches, Set<String> collaborators) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.url = url;
        this.language = language;
        this.branches = branches;
        this.collaborators = collaborators;
    } 

}
