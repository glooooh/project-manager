package com.projectmanager.service;

import java.io.IOException;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Projeto;
import com.projectmanager.repositories.ProjetoRepository;

@Service("ProjetoService")
public class ProjetoServiceImpl implements ProjetoService{
    @Autowired
    GithubAPIService githubService;
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
    public Projeto save(GHMyself loggedUser, String repoName) throws IOException {
        Projeto projeto = new Projeto();
        
        GHRepository repo = githubService.getRepository(loggedUser, repoName);

        if (!exist((int) repo.getId())) {
        
                System.out.println("Criando projeto");
                projeto.setId((int) repo.getId());
                projeto.setNome(repo.getName());
                projeto.setDescricao(repo.getDescription());
                projeto.setData_inicio(repo.getCreatedAt().toString());

                githubService.saveIssuesAsTarefas(repo);

                return projetoRepository.save(projeto);
            
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
