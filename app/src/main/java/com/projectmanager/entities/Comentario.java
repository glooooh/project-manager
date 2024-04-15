package com.projectmanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String comentario;
    private int escritor;
    private int tarefa;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return this.comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getEscritor() {
        return this.escritor;
    }

    public void setEscritor(int escritor) {
        this.escritor = escritor;
    }

    public int getTarefa() {
        return this.tarefa;
    }

    public void setTarefa(int tarefa) {
        this.tarefa = tarefa;
    }

    
}
