package br.com.fiap.upskill.mapper;

import br.com.fiap.upskill.domain.entity.Usuario;
import br.com.fiap.upskill.dto.UsuarioDTO;

public class UsuarioMapper {
    public static UsuarioDTO toDTO(Usuario entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setAreaAtuacao(entity.getAreaAtuacao());
        dto.setNivelCarreira(entity.getNivelCarreira());
        dto.setDataCadastro(entity.getDataCadastro());
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario entity = new Usuario();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setAreaAtuacao(dto.getAreaAtuacao());
        entity.setNivelCarreira(dto.getNivelCarreira());
        if (dto.getDataCadastro() != null) { entity.setDataCadastro(dto.getDataCadastro()); }
        return entity;
    }
}
