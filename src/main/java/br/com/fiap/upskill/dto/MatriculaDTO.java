package br.com.fiap.upskill.dto;

import br.com.fiap.upskill.domain.enums.StatusMatricula;
import java.time.LocalDate;

public class MatriculaDTO {
    private Long id;
    private Long usuarioId;
    private Long trilhaId;
    private LocalDate dataInscricao;
    private StatusMatricula status;

    public MatriculaDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getTrilhaId() { return trilhaId; }
    public void setTrilhaId(Long trilhaId) { this.trilhaId = trilhaId; }

    public LocalDate getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(LocalDate dataInscricao) { this.dataInscricao = dataInscricao; }

    public StatusMatricula getStatus() { return status; }
    public void setStatus(StatusMatricula status) { this.status = status; }
}
