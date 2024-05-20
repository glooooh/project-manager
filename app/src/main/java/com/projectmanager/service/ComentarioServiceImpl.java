package com.projectmanager.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectmanager.entities.Comentario;
import com.projectmanager.model.ComentarioModel;
import com.projectmanager.repositories.ComentarioRepository;

@Service("comentarioService")
public class ComentarioServiceImpl implements ComentarioService{

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public Iterable<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    @Override
    public Comentario find(int id) {
        return comentarioRepository.findById(id).get();
    }

    @Override
    public Comentario save(int tarefaId, String userName, String message) {
        Comentario comentario = new Comentario();
        comentario.setTarefa(tarefaId);
        comentario.setEscritor(userName);
        comentario.setComentario(message);
        
        return comentarioRepository.save(comentario);
    }

    @Override
    public void delete(int id) {
        comentarioRepository.deleteById(id);
    }

    @Override
    public Collection<ComentarioModel> getComentarioTarefa(int tarefaId) {
        ArrayList<ComentarioModel> comentarios = new ArrayList<>();
        
        
        for(Comentario comentario : findAll()){
            if(comentario.getTarefa() == tarefaId){
                ComentarioModel novoComentario = new ComentarioModel();
                novoComentario.setId(comentario.getId());
                novoComentario.setComentario(comentario.getComentario());
                try {
                    novoComentario.setEscritor(usuarioService.find(Integer.parseInt(comentario.getEscritor())).getUsername());
                } catch (Exception e) {
                    novoComentario.setEscritor(comentario.getEscritor());
                }
                
                comentarios.add(novoComentario);
            }
        }

        return comentarios;
    }

    @Override
    public void deleteComentariosTarefa(int idTarefa) {
        for (Comentario comentario : findAll()) {
            if(comentario.getTarefa() == idTarefa){
                delete(comentario.getId());
            }
        }
    }

    

}
