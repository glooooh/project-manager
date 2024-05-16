package com.projectmanager.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Projeto;
import com.projectmanager.repositories.ProjetoRepository;

@Service("ProjetoService")
public class ProjetoServiceImpl implements ProjetoService {
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

    @Override
    public Iterable<GHRepository> findProjectByUserRepositories(Iterable<Projeto> projects,
            Collection<GHRepository> repositories) {
        Collection<GHRepository> result = new ArrayList<>();

        for (Projeto projeto : projects) {
            for (GHRepository repo : repositories) {
                if (repo.getName().equals(projeto.getNome())) {
                    result.add(repo);
                    break;
                }
            }
        }

        return result;
    }

    private Iterable<Projeto> orderByDate() {
        List<Projeto> orderedProjects = (List<Projeto>) findAll();

        for (Projeto projeto : orderedProjects) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);                LocalDateTime dataInicio = LocalDateTime.parse(projeto.getData_inicio(), formatter);
                projeto.setDataInicioDate(dataInicio);
            } catch (DateTimeParseException e) {
                System.out.println("Erro ao analisar a data de início do projeto: " + e.getMessage());
            }
        }

        Collections.sort(orderedProjects,
                (projeto1, projeto2) -> projeto2.getDataInicioDate().compareTo(projeto1.getDataInicioDate()));

        return orderedProjects;
    }

    @Override
    public List<GHRepository> findTop3ByOrderByDataCriacaoDesc(Collection<GHRepository> repositories) {
        List<GHRepository> orderedProjects = (List<GHRepository>) findProjectByUserRepositories(orderByDate(),
                repositories);

        List<GHRepository> top3Projects = orderedProjects.subList(0, Math.min(3, orderedProjects.size()));

        return top3Projects;
    }

}
