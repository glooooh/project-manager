package com.projectmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import githubService.SpringBootApplication;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;

import com.projectmanager.entities.Comentario;
import com.projectmanager.entities.Projeto;
import com.projectmanager.service.ComentarioService;
import com.projectmanager.service.ProjetoService;

@SpringBootApplication()
public class AppApplication implements CommandLineRunner{

	@Autowired
	ComentarioService comentarioService;
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
//oi
	//Teste para verificar integração com o BD
	@Override
	public void run(String... args) throws Exception {
		//Demo4();
	}

	/*
	private void Demo1(){
		for(Comentario comentario : comentarioService.findAll()){
			System.out.println("id: "+ comentario.getId());
			System.out.println("nome: "+comentario.getComentario());
		}
	}

	private void Demo2(){
		Comentario comentario = comentarioService.find(600);
		System.out.println("comentario: "+ comentario.getComentario());
	}

	private void Demo3(){
		Comentario comentario = new Comentario();
		comentario.setComentario("Este é um comentario bem bacana");
		comentario.setEscritor(700);
		comentario.setTarefa(500);
		comentarioService.save(comentario);
		System.out.println("Feito!");
	}

	private void Demo4(){
		try {
			comentarioService.delete(2);
			System.out.println("Excluido.");
		} catch (Exception e) {
			System.out.println("Não excluiu");
		}
	}
	*/
}
