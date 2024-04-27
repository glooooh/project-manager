package com.projectmanager.service;

import java.io.IOException;

import org.kohsuke.github.GHRepository;
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
    public Projeto save(GHRepository repo) {
        Projeto projeto = new Projeto();

        if (!exist((int) repo.getId())) {
            try {
                System.out.println("Criando projeto");
                projeto.setId((int) repo.getId());
                projeto.setNome(repo.getName());
                projeto.setDescricao(repo.getDescription());
                projeto.setData_inicio(repo.getCreatedAt().toString());

                return projetoRepository.save(projeto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return null;
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
