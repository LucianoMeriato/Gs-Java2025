package br.com.fiap.upskill.domain.entity;

import br.com.fiap.upskill.domain.enums.NivelCarreira;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nome;

    @Email
    @NotBlank
    @Column(unique = true, length = 150)
    private String email;

    @Size(max = 100)
    @Column(name = "area_atuacao")
    private String areaAtuacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_carreira", length = 20)
    private NivelCarreira nivelCarreira;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro = LocalDate.now();

    public Usuario() {}

    public Usuario(Long id, String nome, String email, String areaAtuacao, NivelCarreira nivelCarreira, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.areaAtuacao = areaAtuacao;
        this.nivelCarreira = nivelCarreira;
        this.dataCadastro = dataCadastro;
    }

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
