package com.projectmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projectmanager.entities.Cronograma;

@Repository("cronogramaRepository")
public interface CronogramaRepository extends CrudRepository<Cronograma, Integer>{

}
