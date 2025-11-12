package br.com.fiap.upskill.service;

import br.com.fiap.upskill.domain.entity.Matricula;
import br.com.fiap.upskill.domain.entity.Trilha;
import br.com.fiap.upskill.domain.entity.Usuario;
import br.com.fiap.upskill.domain.enums.StatusMatricula;
import br.com.fiap.upskill.dto.MatriculaDTO;
import br.com.fiap.upskill.dto.MatricularRequest;
import br.com.fiap.upskill.exception.TrilhaNaoEncontradaException;
import br.com.fiap.upskill.exception.UsuarioNaoEncontradoException;
import br.com.fiap.upskill.mapper.MatriculaMapper;
import br.com.fiap.upskill.repository.MatriculaRepository;
import br.com.fiap.upskill.repository.TrilhaRepository;
import br.com.fiap.upskill.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    private final MatriculaRepository repo;
    private final UsuarioRepository usuarioRepo;
    private final TrilhaRepository trilhaRepo;

    public MatriculaService(MatriculaRepository repo, UsuarioRepository usuarioRepo, TrilhaRepository trilhaRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.trilhaRepo = trilhaRepo;
    }

    @Transactional
    public MatriculaDTO matricular(MatricularRequest req) {
        Usuario u = usuarioRepo.findById(req.getUsuarioId()).orElseThrow(() -> new UsuarioNaoEncontradoException(req.getUsuarioId()));
        Trilha t = trilhaRepo.findById(req.getTrilhaId()).orElseThrow(() -> new TrilhaNaoEncontradaException(req.getTrilhaId()));
        Matricula mat = new Matricula();
        mat.setUsuario(u);
        mat.setTrilha(t);
        mat.setStatus(StatusMatricula.ATIVA);
        Matricula saved = repo.save(mat);
        return MatriculaMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<MatriculaDTO> listarPorUsuario(Long usuarioId) {
        Usuario u = usuarioRepo.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        return repo.findByUsuario(u).stream().map(MatriculaMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MatriculaDTO> listarPorTrilha(Long trilhaId) {
        Trilha t = trilhaRepo.findById(trilhaId).orElseThrow(() -> new TrilhaNaoEncontradaException(trilhaId));
        return repo.findByTrilha(t).stream().map(MatriculaMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void cancelar(Long matriculaId) {
        Matricula m = repo.findById(matriculaId).orElseThrow(() -> new RuntimeException("Matrícula não encontrada: id=" + matriculaId));
        m.setStatus(StatusMatricula.CANCELADA);
    }
}
