package br.com.fiap.upskill.controller;

import br.com.fiap.upskill.dto.RecomendacaoDTO;
import br.com.fiap.upskill.service.RecomendacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendacoes")
@CrossOrigin
public class RecomendacaoController {

    private final RecomendacaoService service;

    public RecomendacaoController(RecomendacaoService service) {
        this.service = service;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RecomendacaoDTO>> recomendarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.recomendarPorUsuario(usuarioId));
    }
}
