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

}
