package br.com.fiap.upskill.mapper;

import br.com.fiap.upskill.domain.entity.Matricula;
import br.com.fiap.upskill.dto.MatriculaDTO;

public class MatriculaMapper {
    public static MatriculaDTO toDTO(Matricula entity) {
        MatriculaDTO dto = new MatriculaDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setTrilhaId(entity.getTrilha().getId());
        dto.setDataInscricao(entity.getDataInscricao());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
