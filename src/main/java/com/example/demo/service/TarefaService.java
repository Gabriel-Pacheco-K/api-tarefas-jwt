package com.example.demo.service;

import com.example.demo.dto.TarefaDTO;
import com.example.demo.model.Tarefa;
import com.example.demo.model.Usuario;
import com.example.demo.repository.TarefaRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaService(TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario buscarUsuario(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public List<Tarefa> listar(String email) {
        return tarefaRepository.findByUsuario(buscarUsuario(email));
    }

    public Tarefa criar(TarefaDTO dto, String email) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setConcluida(dto.concluida());
        tarefa.setUsuario(buscarUsuario(email));
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizar(Long id, TarefaDTO dto, String email) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));

        // Garante que o usuário só edita as próprias tarefas
        if (!tarefa.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para editar esta tarefa.");
        }

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setConcluida(dto.concluida());
        return tarefaRepository.save(tarefa);
    }

    public void deletar(Long id, String email) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));

        if (!tarefa.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para deletar esta tarefa.");
        }

        tarefaRepository.delete(tarefa);
    }
}