package com.projectmanager.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Feedback;
import com.projectmanager.repositories.FeedbackRepository;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public Iterable<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback find(int id) {
        return feedbackRepository.findById(id).get();
    }

    @Override
    public Feedback save(int projetoId, String criadorName, String message, int destinatarioId) {
        Feedback feedback = new Feedback();
        feedback.setComentario(message);
        feedback.setDestinatario(destinatarioId);
        feedback.setEscritor(criadorName);
        feedback.setProjeto(projetoId);

        return feedbackRepository.save(feedback);
    }

    @Override
    public void delete(int id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public Collection<Feedback> getFeedbacksUsuario(int destinatarioId) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();

        for (Feedback feedback : findAll()) {
            if (feedback.getDestinatario() == destinatarioId) {
                feedbacks.add(feedback);
            }
        }

        return feedbacks;
    }

}
