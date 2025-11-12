package br.com.fiap.upskill.dto;

import br.com.fiap.upskill.domain.enums.NivelTrilha;
import jakarta.validation.constraints.*;
import java.util.List;

public class TrilhaDTO {
    private Long id;

    @NotBlank @Size(max = 150)
    private String nome;

    private String descricao;

    @NotNull
    private NivelTrilha nivel;

    @NotNull @Min(1)
    private Integer cargaHoraria;

    private String focoPrincipal;

    private List<Long> competenciasIds;

    public TrilhaDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public NivelTrilha getNivel() { return nivel; }
    public void setNivel(NivelTrilha nivel) { this.nivel = nivel; }

    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public String getFocoPrincipal() { return focoPrincipal; }
    public void setFocoPrincipal(String focoPrincipal) { this.focoPrincipal = focoPrincipal; }

    public List<Long> getCompetenciasIds() { return competenciasIds; }
    public void setCompetenciasIds(List<Long> competenciasIds) { this.competenciasIds = competenciasIds; }
}
