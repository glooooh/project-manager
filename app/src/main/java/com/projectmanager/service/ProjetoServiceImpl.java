package com.projectmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Projeto;
import com.projectmanager.repositories.ProjetoRepository;

@Service("ProjetoService")
public class ProjetoServiceImpl implements ProjetoService{

    @Autowired
    ProjetoRepository projetoRepository;

    @Override
    public Iterable<Projeto> findAll() {
        return projetoRepository.findAll();
    }

    @Override
    public Projeto find(int id) {
        return projetoRepository.findById(id).get();
    }

    @Override
    public Projeto save(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    @Override
    public void delete(int id) {
        projetoRepository.deleteById(id);
    }

    @Override
    public boolean exist(int id) {
        return projetoRepository.existsById(id);
    }

}
