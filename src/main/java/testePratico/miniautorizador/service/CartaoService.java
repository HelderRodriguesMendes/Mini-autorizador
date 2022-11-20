package testePratico.miniautorizador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import testePratico.miniautorizador.dto.CartaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.model.Cartao;
import testePratico.miniautorizador.repository.CartaoRepositoy;

import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepositoy cartaoRepositoy;

    public RespostaDTO cadastrar(CartaoDTO cartaoDTO){
        Cartao cartao = cartaoRepositoy.save(new Cartao(cartaoDTO));
        return new RespostaDTO(HttpStatus.CREATED, new CartaoDTO(cartao));
    }

    public RespostaDTO buscarSaldo(String numeroCartao){
        Optional<Cartao> cartaoOptional = this.BuscarCartao(numeroCartao);
        if(!cartaoOptional.isPresent()){
            return new RespostaDTO(HttpStatus.NOT_FOUND, null);
        }
        return new RespostaDTO(HttpStatus.OK, cartaoOptional.get().getSaldo());
    }

    public Optional<Cartao> BuscarCartao(String numeroCartao){
        return cartaoRepositoy.findByNumeroCartao(numeroCartao);
    }
}
