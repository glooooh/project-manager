package com.projectmanager.service;

import java.io.IOException;
import java.util.Collection;

import com.projectmanager.entities.Feedback;
import com.projectmanager.forms.FeedbackForm;


public interface FeedbackService {
    public Iterable<Feedback> findAll();
    public Feedback find(int id);
    public Feedback save(String repoName, int criadorId, FeedbackForm feedbackForm);
    public void delete(int id);
    public Collection<Feedback> getFeedbacksUsuarioProjeto(String accessToken, String repoName)throws IOException ;
}
