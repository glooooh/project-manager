package com.projectmanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projeto")
public class Projeto {

    @Id
    private int id;
    private String nome;
    private String descricao;
    private String data_inicio;
    private String data_fim;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_inicio() {
        return this.data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_fim() {
        return this.data_fim;
    }

    public void setData_fim(String data_fim) {
        this.data_fim = data_fim;
    }
    
}
