package com.projectmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    //Descomentar isso e remover o /home dos urls com autenticacao
    /*@GetMapping
    public String defaultRedirect(){
        return "redirect:/home";
    }*/


    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    @GetMapping("/login")
    public String githubLogin() {
        // https://github.com/login/oauth/authorize?client_id=SEU_CLIENT_ID&redirect_uri=URL_DE_RETORNO&scope=public_repo,user
        return "redirect:https://github.com/login/oauth/authorize?client_id=eab0701da41462780733&redirect_uri=http://localhost:8080/home&scope=public_repo,user";    }
}
