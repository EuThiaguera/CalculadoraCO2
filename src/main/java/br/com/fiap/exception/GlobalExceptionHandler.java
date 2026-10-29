package br.com.fiap.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErroResposta> tratarValidacao(ValidacaoException e) {
        return montar(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarNaoEncontrado(RecursoNaoEncontradoException e) {
        return montar(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErroResposta> tratarRequisicaoInvalida(Exception e) {
        return montar(HttpStatus.BAD_REQUEST, "Requisição inválida. Verifique o JSON enviado e os tipos dos campos.");
    }

    @ExceptionHandler(ErroBancoException.class)
    public ResponseEntity<ErroResposta> tratarBanco(ErroBancoException e) {
        log.error("Erro de banco de dados", e);
        return montar(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao acessar o banco de dados.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarGenerico(Exception e) {
        log.error("Erro inesperado", e);
        return montar(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor.");
    }

    private ResponseEntity<ErroResposta> montar(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status)
                .body(new ErroResposta(status.value(), status.getReasonPhrase(), mensagem));
    }
}