package testePratico.miniautorizador.utils;

import testePratico.miniautorizador.dto.TransacaoDTO;
import testePratico.miniautorizador.service.CartaoService;

public interface Validacao {
    void validar(TransacaoDTO transacaoDTO, CartaoService cartaoService);
}