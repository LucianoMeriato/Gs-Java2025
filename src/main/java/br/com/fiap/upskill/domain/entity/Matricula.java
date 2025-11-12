package br.com.fiap.upskill.domain.entity;

import br.com.fiap.upskill.domain.enums.StatusMatricula;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trilha_id")
    private Trilha trilha;

    @NotNull
    @Column(name = "data_inscricao", nullable = false)
    private LocalDate dataInscricao = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private StatusMatricula status = StatusMatricula.ATIVA;

    public Matricula() {}

    public Matricula(Long id, Usuario usuario, Trilha trilha, LocalDate dataInscricao, StatusMatricula status) {
        this.id = id;
        this.usuario = usuario;
        this.trilha = trilha;
        this.dataInscricao = dataInscricao;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Trilha getTrilha() { return trilha; }
    public void setTrilha(Trilha trilha) { this.trilha = trilha; }

    public LocalDate getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(LocalDate dataInscricao) { this.dataInscricao = dataInscricao; }

    public StatusMatricula getStatus() { return status; }
    public void setStatus(StatusMatricula status) { this.status = status; }
}
