package com.projectmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import githubService.SpringBootApplication;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;

import com.projectmanager.entities.Projeto;
import com.projectmanager.service.ProjetoService;

@SpringBootApplication()
public class AppApplication implements CommandLineRunner{

	@Autowired
	ProjetoService projetoService;
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	//Teste para verificar integração com o BD
	@Override
	public void run(String... args) throws Exception {
		Demo1();
	}

	private void Demo1(){
		for(Projeto projeto : projetoService.findAll()){
			System.out.println("id: "+ projeto.getId());
			System.out.println("nome: "+projeto.getNome());
		}
	}
}
