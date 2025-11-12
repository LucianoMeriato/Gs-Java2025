package br.com.fiap.upskill.service;

import br.com.fiap.upskill.domain.entity.Usuario;
import br.com.fiap.upskill.dto.UsuarioDTO;
import br.com.fiap.upskill.exception.UsuarioNaoEncontradoException;
import br.com.fiap.upskill.mapper.UsuarioMapper;
import br.com.fiap.upskill.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return repo.findAll().stream().map(UsuarioMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usr = repo.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        return UsuarioMapper.toDTO(usr);
    }

    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto) {
        Usuario entity = UsuarioMapper.toEntity(dto);
        entity.setId(null);
        Usuario saved = repo.save(entity);
        return UsuarioMapper.toDTO(saved);
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario entity = repo.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setAreaAtuacao(dto.getAreaAtuacao());
        entity.setNivelCarreira(dto.getNivelCarreira());
        return UsuarioMapper.toDTO(entity);
    }

    @Transactional
    public void remover(Long id) {
        Usuario entity = repo.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        repo.delete(entity);
    }
}
