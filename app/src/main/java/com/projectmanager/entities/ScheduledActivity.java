package com.projectmanager.entities;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ScheduledActivity {
    private String titulo;
    private String descricao;
    private String data;
    
}