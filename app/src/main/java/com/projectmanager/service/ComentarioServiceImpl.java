package com.projectmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Comentario;
import com.projectmanager.repositories.ComentarioRepository;

@Service("comentarioService")
public class ComentarioServiceImpl implements ComentarioService{

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public Iterable<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    @Override
    public Comentario find(int id) {
        return comentarioRepository.findById(id).get();
    }

    @Override
    public Comentario save(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public void delete(int id) {
        comentarioRepository.deleteById(id);
    }

}
