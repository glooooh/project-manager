package com.projectmanager.service;

import com.projectmanager.entities.Usuario;

public interface UsuarioService {
    public Iterable<Usuario> findAll();
    public Usuario find(int id);
    public Usuario save(Usuario usuario);
    public void delete(int id);
    public boolean exist(int id);
}
