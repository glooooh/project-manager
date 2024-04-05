package com.projectmanager.model;

import java.util.Set;

import lombok.Data;
@Data

public class TarefaModel {
    private long id;
    private String owner;
    private String name;
    private String description;
    private Set<String> participants;
    private String status;

    
    public TarefaModel() {
    }

    public TarefaModel(long id, String owner, String name, String description, Set<String> participants,
            String status) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.participants = participants;
        this.status = status;
    }
   

}
