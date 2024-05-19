package com.projectmanager.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Cronograma;
import com.projectmanager.repositories.CronogramaRepository;

@Service("cronogramaService")
public class CronogramaServiceImpl implements CronogramaService{

    @Autowired
    CronogramaRepository cronogramaRepository;

    @Override
    public Iterable<Cronograma> findAll() {
        return cronogramaRepository.findAll();
    }

    @Override
    public Cronograma find(int id) {
        return cronogramaRepository.findById(id).get();
    }

    @Override
    public Cronograma save(Cronograma newCronograma) {
        return cronogramaRepository.save(newCronograma);
    }

    @Override
    public void delete(int id) {
        cronogramaRepository.deleteById(id);
    }

    @Override
    public void deleteCronogramasProjeto(int idProjeto) {
        for (Cronograma cronograma : findAll()) {
            if(cronograma.getProjeto_id() == idProjeto){
                delete(cronograma.getId());
            }
        }
    }

    @Override
    public Collection<Cronograma> getCronogramasProjeto(int idProjeto) {
        ArrayList<Cronograma> cronogramas = new ArrayList<>();
        
        for(Cronograma cronograma : findAll()){
			if(cronograma.getProjeto_id() == idProjeto){
                cronogramas.add(cronograma);
            }
		}

        return cronogramas;
    }

}
