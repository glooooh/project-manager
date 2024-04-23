package com.projectmanager.model;

import java.util.List;
import java.util.Set;

import lombok.Data;
@Data

public class TarefaModel {
    private int id;
    private String titulo;
    private int id_criador;
    private String descricao;
    private String prazo;
    private String data_criacao;
    private String status;
    private int id_projeto;
    private List<String> colaboradores;
    public TarefaModel(int id, String titulo, int id_criador, String descricao, String prazo, String data_criacao,
            String status, int id_projeto, List<String> colaboradores) {
        this.id = id;
        this.titulo = titulo;
        this.id_criador = id_criador;
        this.descricao = descricao;
        this.prazo = prazo;
        this.data_criacao = data_criacao;
        this.status = status;
        this.id_projeto = id_projeto;
        this.colaboradores = colaboradores;
    }

    
    
}
