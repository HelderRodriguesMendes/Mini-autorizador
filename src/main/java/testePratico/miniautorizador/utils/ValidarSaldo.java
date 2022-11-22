package testePratico.miniautorizador.utils;

import org.springframework.http.HttpStatus;
import testePratico.miniautorizador.dto.TransacaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.enums.RegrasDeAutorizacao;
import testePratico.miniautorizador.exception.RegraNegocioException;
import testePratico.miniautorizador.service.CartaoService;

public class ValidarSaldo implements Validacao{

    @Override
    public void validar(TransacaoDTO transacaoDTO, CartaoService cartaoService) {
        cartaoService.BuscarCartao_SALDO(transacaoDTO.getNumeroCartao(), transacaoDTO.getValor()).orElseThrow(() ->
                new RegraNegocioException("", new RespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY, RegrasDeAutorizacao.SALDO_INSUFICIENTE.getMotivo()))
            );
    }
}