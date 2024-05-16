package com.projectmanager.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Feedback;
import com.projectmanager.forms.FeedbackForm;
import com.projectmanager.repositories.FeedbackRepository;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    GithubAPIService githubService;

    @Override
    public Iterable<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback find(int id) {
        return feedbackRepository.findById(id).get();
    }

    @Override
    public Feedback save(String repoName, String accessToken, FeedbackForm feedbackForm) throws IOException{
        GHMyself user = githubService.getUser(accessToken);
        GHRepository repo = githubService.getRepository(user, repoName);
        Long colaboratorId = githubService.getCollaboratorId(feedbackForm.getCollaborators().get(0),repo);
        Feedback feedback = new Feedback();
        feedback.setComentario(feedbackForm.getMensagem());
        feedback.setDestinatario(colaboratorId.intValue());
        feedback.setEscritor(user.getLogin());
        feedback.setProjeto(Math.toIntExact(repo.getId()));

        return feedbackRepository.save(feedback);
    }

    @Override
    public void delete(int id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public Collection<Feedback> getFeedbacksUsuarioProjeto(String accessToken, String repoName) throws IOException{
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        GHMyself user = githubService.getUser(accessToken);
        int userId = (int) user.getId();
        GHRepository repo = githubService.getRepository( user,  repoName);

        for (Feedback feedback : findAll()) {
            if (feedback.getDestinatario() == userId && feedback.getProjeto() == (int) repo.getId()){
                System.out.println("Achei um feedback");
                feedbacks.add(feedback);
            }
        }

        return feedbacks;
    }

}
