package com.projectmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.projectmanager.model.UsuarioModel;

@Controller
public class UserController {

    @GetMapping("/user/{user_id}")
    public String getUserPage(@PathVariable("user_id") String userId, Model model) {
        // Precisa ver como vai passar os dados do User_Model do HomeController para cá
        //UserModel user = new UserModel("username", Long.parseLong(userId), "dummyToken", "user@example.com", "John");

        // Adicione o usuário ao modelo para ser usado na página HTML
        //model.addAttribute("user", user);

        // Retorne o nome da página HTML
        return "user";
    }
}
