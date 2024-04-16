package com.projectmanager.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class ParametroId {
    private int tarefa_id;
    private int usuario_id;


    public int getTarefa_id() {
        return this.tarefa_id;
    }

    public void setTarefa_id(int tarefa_id) {
        this.tarefa_id = tarefa_id;
    }

    public int getUsuario_id() {
        return this.usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

}