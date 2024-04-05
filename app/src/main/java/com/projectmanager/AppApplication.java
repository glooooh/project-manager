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
		//Demo1();
	}
/* 
	private void Demo1(){
		for(Projeto projeto : projetoService.findAll()){
			System.out.println("id: "+ projeto.getId());
			System.out.println("nome: "+projeto.getNome());
		}
	}

	private void Demo2(){
		Projeto projeto = projetoService.find(600);
		System.out.println("id: "+ projeto.getId());
		System.out.println("nome: "+projeto.getNome());
	}

	private void Demo3(){
		Projeto projeto = new Projeto();
		projeto.setId(800);
		projeto.setNome("Gerenciador de projetos");
		projeto.setDescricao("Gerencia projetos");
		projeto.setData_inicio("15/08/2009");
		projeto.setData_fim("-");
		projeto = projetoService.save(projeto);
		System.out.println("Salvando " +projeto.getNome());
	}

	private void Demo4(){
		try {
			projetoService.delete(800);
			System.out.println("Excluido.");
		} catch (Exception e) {
			System.out.println("Não excluiu");
		}
	}*/
}
