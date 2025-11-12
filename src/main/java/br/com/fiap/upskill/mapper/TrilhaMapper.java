package br.com.fiap.upskill.mapper;

import br.com.fiap.upskill.domain.entity.Trilha;
import br.com.fiap.upskill.domain.entity.Competencia;
import br.com.fiap.upskill.dto.TrilhaDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrilhaMapper {
    public static TrilhaDTO toDTO(Trilha entity) {
        TrilhaDTO dto = new TrilhaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        dto.setNivel(entity.getNivel());
        dto.setCargaHoraria(entity.getCargaHoraria());
        dto.setFocoPrincipal(entity.getFocoPrincipal());
        List<Long> compIds = entity.getCompetencias().stream().map(Competencia::getId).toList();
        dto.setCompetenciasIds(compIds);
        return dto;
    }

    public static Trilha toEntity(TrilhaDTO dto, Set<Competencia> competencias) {
        Trilha entity = new Trilha();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setNivel(dto.getNivel());
        entity.setCargaHoraria(dto.getCargaHoraria());
        entity.setFocoPrincipal(dto.getFocoPrincipal());
        entity.setCompetencias(competencias);
        return entity;
    }
}
