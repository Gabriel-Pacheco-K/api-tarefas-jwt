package com.example.demo.controller;

import com.example.demo.dto.TarefaDTO;
import com.example.demo.model.Tarefa;
import com.example.demo.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    // Pega o e-mail do usuário logado a partir do token (preenchido pelo JwtFilter)
    private String emailLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var usuario = (com.example.demo.model.Usuario) auth.getPrincipal();
        return usuario.getEmail();
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listar() {
        return ResponseEntity.ok(tarefaService.listar(emailLogado()));
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody TarefaDTO dto) {
        return ResponseEntity.ok(tarefaService.criar(dto, emailLogado()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody TarefaDTO dto) {
        return ResponseEntity.ok(tarefaService.atualizar(id, dto, emailLogado()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id, emailLogado());
        return ResponseEntity.noContent().build();
    }
}