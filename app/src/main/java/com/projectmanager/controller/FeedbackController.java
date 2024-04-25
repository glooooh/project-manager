package com.projectmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user/{user_id}/repositories/{repo_name}/feedback")
public class FeedbackController {

    @GetMapping("")
    public String getFeedback() {
        return "feedback";
    }
}
