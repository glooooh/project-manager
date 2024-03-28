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
}
