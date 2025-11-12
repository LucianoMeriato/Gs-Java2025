package br.com.fiap.upskill.exception;

public class TrilhaNaoEncontradaException extends RuntimeException {
    public TrilhaNaoEncontradaException(Long id) {
        super("Trilha não encontrada: id=" + id);
    }
}
