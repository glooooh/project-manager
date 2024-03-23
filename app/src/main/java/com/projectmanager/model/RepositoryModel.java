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
    
}
