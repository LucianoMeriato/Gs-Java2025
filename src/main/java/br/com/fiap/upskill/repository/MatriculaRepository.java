package br.com.fiap.upskill.repository;

import br.com.fiap.upskill.domain.entity.Matricula;
import br.com.fiap.upskill.domain.entity.Trilha;
import br.com.fiap.upskill.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByUsuario(Usuario usuario);
    List<Matricula> findByTrilha(Trilha trilha);
}
