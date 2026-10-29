package br.com.fiap.exception;

public class ErroBancoException extends RuntimeException {
    public ErroBancoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public ErroBancoException(String mensagem) {
        super(mensagem);
    }
}