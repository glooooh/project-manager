package com.projectmanager.model;

import java.util.Set;

public class ProjetoModel {
    private long id;
    private String nome;
    private String descricao;
    private String data_inicio;
    private String data_fim;
    
    public ProjetoModel() {
    }

    public ProjetoModel(long id, String nome, String descricao, String data_inicio, String data_fim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
    } 
}
