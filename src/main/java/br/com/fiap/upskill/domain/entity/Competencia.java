package br.com.fiap.upskill.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "competencias")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @Size(max = 100)
    private String categoria;

    @Lob
    private String descricao;

    @ManyToMany(mappedBy = "competencias")
    private Set<Trilha> trilhas = new HashSet<>();

    public Competencia() {}

    public Competencia(Long id, String nome, String categoria, String descricao) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Set<Trilha> getTrilhas() { return trilhas; }
    public void setTrilhas(Set<Trilha> trilhas) { this.trilhas = trilhas; }
}
