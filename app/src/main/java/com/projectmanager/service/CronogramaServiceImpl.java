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
    public Cronograma save(int tarefaId, String titulo, String message, String data) {
        Cronograma cronograma = new Cronograma();
        cronograma.setId(tarefaId);
        cronograma.setTitulo(titulo);
        cronograma.setData(message);
        cronograma.setData(data);

        return cronogramaRepository.save(cronograma);
    }

    @Override
    public void delete(int id) {
        cronogramaRepository.deleteById(id);
    }

    @Override
    public void deleteCronogramasTarefa(int idTarefa) {
        for (Cronograma cronograma : findAll()) {
            if(cronograma.getTarefa_id() == idTarefa){
                delete(cronograma.getId());
            }
        }
    }

    @Override
    public Collection<Cronograma> getCronogramasTarefa(int idTarefa) {
        ArrayList<Cronograma> cronogramas = new ArrayList<>();
        
        for(Cronograma cronograma : findAll()){
			if(cronograma.getTarefa_id() == idTarefa){
                cronogramas.add(cronograma);
            }
		}

        return cronogramas;
    }

}
