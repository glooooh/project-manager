package com.projectmanager.service;

import java.util.Collection;

import com.projectmanager.entities.Feedback;


public interface FeedbackService {
    public Iterable<Feedback> findAll();
    public Feedback find(int id);
    public Feedback save(int projetoId, String criadorName, String message, int destinatarioId);
    public void delete(int id);
    public Collection<Feedback> getFeedbacksUsuario(int destinatarioId);
}
