package com.projectmanager.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder

public class GithubRepoResponse {
    
    @JsonProperty("name")
    private String name;

    //Default constructor
    public GithubRepoResponse(){}

    //Custom constructor
    public GithubRepoResponse(String name){
        this.name = name;
    }
    
}
