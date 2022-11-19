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

        Optional<Cartao> cart = this.BuscarCartao(cartaoDTO.getNumeroCartao());
        if(cart.isPresent()){
            return new RespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY, new CartaoDTO(cart.get()));
        }
        Cartao cartao = cartaoRepositoy.save(new Cartao(cartaoDTO));
        return new RespostaDTO(HttpStatus.CREATED, new CartaoDTO(cartao));
    }

    private Optional<Cartao> BuscarCartao(String numeroCartao){
        return cartaoRepositoy.findByNumeroCartao(numeroCartao);
    }
}
