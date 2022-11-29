package testePratico.miniautorizador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import testePratico.miniautorizador.dto.CartaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.exception.RegraNegocioException;
import testePratico.miniautorizador.model.Cartao;
import testePratico.miniautorizador.repository.CartaoRepositoy;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepositoy cartaoRepositoy;

    public RespostaDTO cadastrar(CartaoDTO cartaoDTO){
        try{
            Cartao cartao = cartaoRepositoy.save(new Cartao(cartaoDTO));
            return new RespostaDTO(HttpStatus.CREATED, new CartaoDTO(cartao));
        }catch (Exception e){
            throw new RegraNegocioException("", new RespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY, cartaoDTO));
        }
    }

    public RespostaDTO buscarSaldo(String numeroCartao){
        Cartao cartao = cartaoRepositoy.findByNumeroCartao(numeroCartao).orElseThrow(() -> {
            return new RegraNegocioException("", new RespostaDTO(HttpStatus.NOT_FOUND, null));
        });
        return new RespostaDTO(HttpStatus.OK, cartao.getSaldo());
    }

    public Optional<Cartao> BuscarCartao_NUMERO(String numeroCartao){
        return cartaoRepositoy.findByNumeroCartao(numeroCartao);
    }

    public Optional<Cartao> BuscarCartao_SENHA(String numeroCartao, Integer senha){
        return cartaoRepositoy.findByNumeroCartaoAndSenha(numeroCartao, senha);
    }

    public Optional<Cartao> BuscarCartao_SALDO(String numeroCartao, BigDecimal valor){
        return cartaoRepositoy.findByNumeroCartaoAndSaldoGreaterThanEqual(numeroCartao, valor);
    }

    public Cartao salvarAlteracaoSaldo(Cartao cartao){
        return cartaoRepositoy.save(cartao);
    }
}
