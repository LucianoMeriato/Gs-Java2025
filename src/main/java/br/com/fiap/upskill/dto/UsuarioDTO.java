package br.com.fiap.upskill.dto;

import br.com.fiap.upskill.domain.enums.NivelCarreira;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class UsuarioDTO {
    private Long id;

    @NotBlank @Size(min=2, max=100)
    private String nome;

    @NotBlank @Email
    private String email;

    @Size(max=100)
    private String areaAtuacao;

    private NivelCarreira nivelCarreira;

    private LocalDate dataCadastro;

    public UsuarioDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAreaAtuacao() { return areaAtuacao; }
    public void setAreaAtuacao(String areaAtuacao) { this.areaAtuacao = areaAtuacao; }

    public NivelCarreira getNivelCarreira() { return nivelCarreira; }
    public void setNivelCarreira(NivelCarreira nivelCarreira) { this.nivelCarreira = nivelCarreira; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
}
