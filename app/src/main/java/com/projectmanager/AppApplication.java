package com.projectmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import githubService.SpringBootApplication;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;

import com.projectmanager.entities.Tarefa;
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
		//Demo1();
	}

	/*
	private void Demo1(){
		for(Tarefa tarefa : tarefaService.getTaskByProject(0)){
			System.out.println("id: "+ tarefa.getId());
			System.out.println("id do criador: "+tarefa.getId_criador());
			System.out.println("id projeto: "+tarefa.getId_projeto());
		}
	}

	// private void Demo2(){
	// 	Collection<Comentario> comentarios = comentarioService.getComentarioTarefa(1);
	// 	for(Comentario comentario : comentarios){
	// 		System.out.println("id: "+ comentario.getId());
	// 		System.out.println("usuario: "+ comentario.getEscritor());
	// 		System.out.println("comentario: "+comentario.getComentario());
	// 	}
	// }
	
	private void Demo2(){
		Comentario comentario = comentarioService.find(600);
		System.out.println("comentario: "+ comentario.getComentario());
	}
	*/


	private void Demo3(){
		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo("Linda");
		tarefa.setId(0);
		tarefa.setDescricao("sou linda");
		tarefaService.save(tarefa, 56931444);

		System.out.println("Feito!");
	}

	/*
	
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
