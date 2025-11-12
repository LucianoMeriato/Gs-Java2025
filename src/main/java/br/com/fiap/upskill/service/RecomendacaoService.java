package br.com.fiap.upskill.service;

import br.com.fiap.upskill.domain.entity.Trilha;
import br.com.fiap.upskill.domain.entity.Usuario;
import br.com.fiap.upskill.domain.enums.NivelCarreira;
import br.com.fiap.upskill.domain.enums.NivelTrilha;
import br.com.fiap.upskill.dto.RecomendacaoDTO;
import br.com.fiap.upskill.exception.UsuarioNaoEncontradoException;
import br.com.fiap.upskill.repository.TrilhaRepository;
import br.com.fiap.upskill.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    private final UsuarioRepository usuarioRepo;
    private final TrilhaRepository trilhaRepo;

    public RecomendacaoService(UsuarioRepository usuarioRepo, TrilhaRepository trilhaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.trilhaRepo = trilhaRepo;
    }

    @Transactional(readOnly = true)
    public List<RecomendacaoDTO> recomendarPorUsuario(Long usuarioId) {
        Usuario u = usuarioRepo.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
        List<Trilha> trilhas = trilhaRepo.findAll();

        return trilhas.stream().map(t -> scoreTrilha(u, t))
                .sorted(Comparator.comparing(RecomendacaoDTO::getScore).reversed())
                .collect(Collectors.toList());
    }

    private RecomendacaoDTO scoreTrilha(Usuario u, Trilha t) {
        int score = 0;
        List<String> motivos = new ArrayList<>();

        // 1) Nivel de carreira x nivel da trilha
        Map<NivelCarreira, NivelTrilha> alvo = Map.of(
                NivelCarreira.JUNIOR, NivelTrilha.INICIANTE,
                NivelCarreira.PLENO, NivelTrilha.INTERMEDIARIO,
                NivelCarreira.SENIOR, NivelTrilha.AVANCADO,
                NivelCarreira.TRANSICAO, NivelTrilha.INICIANTE
        );
        NivelTrilha alvoNivel = alvo.getOrDefault(u.getNivelCarreira(), NivelTrilha.INICIANTE);
        if (t.getNivel() == alvoNivel) { score += 30; motivos.add("Nível alinhado (" + t.getNivel() + ")"); }
        else if (isAdjacente(alvoNivel, t.getNivel())) { score += 15; motivos.add("Nível próximo (" + t.getNivel() + ")"); }

        // 2) Foco principal x área de atuação (matching aproximado)
        String foco = Optional.ofNullable(t.getFocoPrincipal()).orElse("").toLowerCase();
        String area = Optional.ofNullable(u.getAreaAtuacao()).orElse("").toLowerCase();
        if (!foco.isBlank() && !area.isBlank() && (area.contains(foco) || foco.contains(area))) {
            score += 25; motivos.add("Foco da trilha combina com sua área (" + t.getFocoPrincipal() + ")");
        }

        // 3) Eixos do futuro do trabalho - pequenos bônus
        Set<String> hot = Set.of("ia","dados","cloud","segurança","soft skills","green tech");
        for (String h : hot) {
            if (foco.contains(h)) { score += 10; motivos.add("Tendência: " + h); break; }
        }

        // 4) Usuário em transição: favorecer iniciantes
        if (u.getNivelCarreira() == NivelCarreira.TRANSICAO && t.getNivel() == NivelTrilha.INICIANTE) {
            score += 20; motivos.add("Em transição: trilha para iniciantes");
        }

        // 5) Penalidade leve se distância de nível for grande
        if (distanciaNivel(alvoNivel, t.getNivel()) >= 2) {
            score -= 10; motivos.add("Distância grande de nível");
        }

        if (score < 0) score = 0;
        return new RecomendacaoDTO(t.getId(), t.getNome(), t.getNivel().name(), score, motivos.toArray(new String[0]));
    }

    private boolean isAdjacente(NivelTrilha a, NivelTrilha b) {
        return Math.abs(ord(a) - ord(b)) == 1;
    }

    private int distanciaNivel(NivelTrilha a, NivelTrilha b) {
        return Math.abs(ord(a) - ord(b));
    }

    private int ord(NivelTrilha n) {
        return switch (n) {
            case INICIANTE -> 0;
            case INTERMEDIARIO -> 1;
            case AVANCADO -> 2;
        };
    }
}
