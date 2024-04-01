package com.projectmanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarefa")
public class Tarefa {
    @Id
    private int id;
    private String nome;
    private int id_criador;
    private String descricao;
    private String data_termino;
    private String data_criacao;


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

    public int getId_criador() {
        return this.id_criador;
    }

    public void setId_criador(int id_criador) {
        this.id_criador = id_criador;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_termino() {
        return this.data_termino;
    }

    public void setData_termino(String data_termino) {
        this.data_termino = data_termino;
    }

    public String getData_criacao() {
        return this.data_criacao;
    }

    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }

}
