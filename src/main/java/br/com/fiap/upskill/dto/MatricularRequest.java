package br.com.fiap.upskill.dto;

import jakarta.validation.constraints.NotNull;

public class MatricularRequest {
    @NotNull
    private Long usuarioId;
    @NotNull
    private Long trilhaId;

    public MatricularRequest() {}

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getTrilhaId() { return trilhaId; }
    public void setTrilhaId(Long trilhaId) { this.trilhaId = trilhaId; }
}
