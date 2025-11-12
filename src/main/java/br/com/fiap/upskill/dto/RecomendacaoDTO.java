package br.com.fiap.upskill.dto;

public class RecomendacaoDTO {
    private Long trilhaId;
    private String trilhaNome;
    private String nivel;
    private Integer score;
    private String[] motivos;

    public RecomendacaoDTO() {}

    public RecomendacaoDTO(Long trilhaId, String trilhaNome, String nivel, Integer score, String[] motivos) {
        this.trilhaId = trilhaId;
        this.trilhaNome = trilhaNome;
        this.nivel = nivel;
        this.score = score;
        this.motivos = motivos;
    }

    public Long getTrilhaId() { return trilhaId; }
    public void setTrilhaId(Long trilhaId) { this.trilhaId = trilhaId; }

    public String getTrilhaNome() { return trilhaNome; }
    public void setTrilhaNome(String trilhaNome) { this.trilhaNome = trilhaNome; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String[] getMotivos() { return motivos; }
    public void setMotivos(String[] motivos) { this.motivos = motivos; }
}
