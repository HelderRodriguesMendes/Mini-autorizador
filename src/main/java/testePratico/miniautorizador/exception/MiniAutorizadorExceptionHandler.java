package testePratico.miniautorizador.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import testePratico.miniautorizador.dto.CartaoDTO;
import testePratico.miniautorizador.exception.model.RegraNegocioException;
import testePratico.miniautorizador.model.Cartao;
import testePratico.miniautorizador.service.CartaoService;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class MiniAutorizadorExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private CartaoService cartaoService;

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request){
        CartaoDTO cartao = obterCartaoExistente(ex.getMessage());
        return handleExceptionInternal(ex, cartao, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    private CartaoDTO obterCartaoExistente(String mensagemDeErro){
        String[] splitted= mensagemDeErro.split("for key");
        String numeroCartao = splitted[0].replaceAll("[^0-9]", "");
        Cartao cartao = cartaoService.BuscarCartao(numeroCartao).get();
        return new CartaoDTO(cartao);
    }

}
