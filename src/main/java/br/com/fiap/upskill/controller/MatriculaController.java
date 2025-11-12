package br.com.fiap.upskill.controller;

import br.com.fiap.upskill.dto.MatriculaDTO;
import br.com.fiap.upskill.dto.MatricularRequest;
import br.com.fiap.upskill.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@CrossOrigin
public class MatriculaController {

    private final MatriculaService service;

    public MatriculaController(MatriculaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> matricular(@Valid @RequestBody MatricularRequest req) {
        MatriculaDTO dto = service.matricular(req);
        return ResponseEntity.created(URI.create("/api/matriculas/" + dto.getId())).body(dto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/trilha/{trilhaId}")
    public ResponseEntity<List<MatriculaDTO>> listarPorTrilha(@PathVariable Long trilhaId) {
        return ResponseEntity.ok(service.listarPorTrilha(trilhaId));
    }

    @PostMapping("/{matriculaId}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long matriculaId) {
        service.cancelar(matriculaId);
        return ResponseEntity.noContent().build();
    }
}
