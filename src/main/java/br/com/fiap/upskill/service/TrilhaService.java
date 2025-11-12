package br.com.fiap.upskill.service;

import br.com.fiap.upskill.domain.entity.Competencia;
import br.com.fiap.upskill.domain.entity.Trilha;
import br.com.fiap.upskill.dto.TrilhaDTO;
import br.com.fiap.upskill.exception.CompetenciaNaoEncontradaException;
import br.com.fiap.upskill.exception.TrilhaNaoEncontradaException;
import br.com.fiap.upskill.mapper.TrilhaMapper;
import br.com.fiap.upskill.repository.CompetenciaRepository;
import br.com.fiap.upskill.repository.TrilhaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrilhaService {

    private final TrilhaRepository repo;
    private final CompetenciaRepository compRepo;

    public TrilhaService(TrilhaRepository repo, CompetenciaRepository compRepo) {
        this.repo = repo;
        this.compRepo = compRepo;
    }

    private Set<Competencia> carregarCompetencias(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        Set<Competencia> set = new HashSet<>();
        for (Long id : ids) {
            Competencia c = compRepo.findById(id).orElseThrow(() -> new CompetenciaNaoEncontradaException(id));
            set.add(c);
        }
        return set;
    }

    @Transactional(readOnly = true)
    public List<TrilhaDTO> listarTodos() {
        return repo.findAll().stream().map(TrilhaMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TrilhaDTO buscarPorId(Long id) {
        Trilha tr = repo.findById(id).orElseThrow(() -> new TrilhaNaoEncontradaException(id));
        return TrilhaMapper.toDTO(tr);
    }

    @Transactional
    public TrilhaDTO criar(TrilhaDTO dto) {
        Set<Competencia> competencias = carregarCompetencias(dto.getCompetenciasIds());
        Trilha entity = TrilhaMapper.toEntity(dto, competencias);
        entity.setId(null);
        Trilha saved = repo.save(entity);
        return TrilhaMapper.toDTO(saved);
    }

    @Transactional
    public TrilhaDTO atualizar(Long id, TrilhaDTO dto) {
        Trilha entity = repo.findById(id).orElseThrow(() -> new TrilhaNaoEncontradaException(id));
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setNivel(dto.getNivel());
        entity.setCargaHoraria(dto.getCargaHoraria());
        entity.setFocoPrincipal(dto.getFocoPrincipal());
        entity.setCompetencias(carregarCompetencias(dto.getCompetenciasIds()));
        return TrilhaMapper.toDTO(entity);
    }

    @Transactional
    public void remover(Long id) {
        Trilha entity = repo.findById(id).orElseThrow(() -> new TrilhaNaoEncontradaException(id));
        repo.delete(entity);
    }
}
