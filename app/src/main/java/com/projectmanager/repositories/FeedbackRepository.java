package com.projectmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projectmanager.entities.Feedback;

@Repository("feedbackRepository")
public interface FeedbackRepository extends CrudRepository<Feedback, Integer>{

}
