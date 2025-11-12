package br.com.fiap.upskill.exception;

public class CompetenciaNaoEncontradaException extends RuntimeException {
    public CompetenciaNaoEncontradaException(Long id) {
        super("Competência não encontrada: id=" + id);
    }
}
