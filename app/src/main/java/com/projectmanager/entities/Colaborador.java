package com.projectmanager.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "colaborador")
public class Colaborador {
    @EmbeddedId
    private ParametroId parametroId;


    public Colaborador() {
        parametroId = new ParametroId();
    }
    

    public int getTarefa_id() {
        return this.parametroId.getTarefa_id();
    }

    public void setTarefa_id(int tarefa_id) {
        this.parametroId.setTarefa_id(tarefa_id);
    }

    public int getUsuario_id() {
        return this.parametroId.getUsuario_id();
    }

    public void setUsuario_id(int usuario_id) {
        this.parametroId.setUsuario_id(usuario_id);;
    }

}
