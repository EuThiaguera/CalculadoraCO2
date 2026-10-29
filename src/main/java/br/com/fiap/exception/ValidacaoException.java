package br.com.fiap.exception;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}