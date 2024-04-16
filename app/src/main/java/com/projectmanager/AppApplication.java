package com.projectmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import githubService.SpringBootApplication;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;

import com.projectmanager.entities.Comentario;
import com.projectmanager.entities.Projeto;
import com.projectmanager.entities.Tarefa;
import com.projectmanager.service.ComentarioService;
import com.projectmanager.service.ProjetoService;
import com.projectmanager.service.TarefaService;

@SpringBootApplication()
public class AppApplication implements CommandLineRunner{

	@Autowired
	TarefaService tarefaService;
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
//oi
	//Teste para verificar integração com o BD
	@Override
	public void run(String... args) throws Exception {
		//Demo3();
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
		Tarefa tarefa = new Tarefa();
		tarefa.setPrazo("14/11/2024");
        tarefa.setTitulo("Tarefa Nova");
        tarefa.setDescricao("To só criando uma tarefa pra testar");
		tarefaService.save(tarefa, 112330160);
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
