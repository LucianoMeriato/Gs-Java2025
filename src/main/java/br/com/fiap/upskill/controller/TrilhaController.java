package br.com.fiap.upskill.controller;

import br.com.fiap.upskill.dto.TrilhaDTO;
import br.com.fiap.upskill.service.TrilhaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/trilhas")
@CrossOrigin
public class TrilhaController {

    private final TrilhaService service;

    public TrilhaController(TrilhaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TrilhaDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrilhaDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TrilhaDTO> criar(@Valid @RequestBody TrilhaDTO dto) {
        TrilhaDTO saved = service.criar(dto);
        return ResponseEntity.created(URI.create("/api/trilhas/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrilhaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TrilhaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
