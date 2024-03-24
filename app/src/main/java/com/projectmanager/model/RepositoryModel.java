package com.projectmanager.model;

import lombok.Data;

@Data

public class RepositoryModel {
    private long id;
    private String owner;
    private String name;
    private String description;
    private String url;
    private String language;
    
    public RepositoryModel() {
    }

    public RepositoryModel(long id, String owner, String name, String description, String url, String language) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.url = url;
        this.language = language;
    }

    
    
    
}
