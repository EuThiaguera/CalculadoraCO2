package br.com.fiap.exception;

import java.time.LocalDateTime;

public record ErroResposta(int status, String erro, String mensagem, LocalDateTime timestamp) {
    public ErroResposta(int status, String erro, String mensagem) {
        this(status, erro, mensagem, LocalDateTime.now());
    }
}