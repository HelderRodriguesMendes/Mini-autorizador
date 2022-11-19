package testePratico.miniautorizador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import testePratico.miniautorizador.dto.CartaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.model.Cartao;
import testePratico.miniautorizador.repository.CartaoRepositoy;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepositoy cartaoRepositoy;

    public RespostaDTO cadastrar(CartaoDTO cartaoDTO){

        Cartao cart = this.BuscarCartao(cartaoDTO.getNumeroCartao());
        if(cart != null){
            return new RespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY, new CartaoDTO(cart));
        }
        Cartao cartao = cartaoRepositoy.save(new Cartao(cartaoDTO));
        return new RespostaDTO(HttpStatus.CREATED, new CartaoDTO(cartao));
    }

    private Cartao BuscarCartao(String numeroCartao){
        return cartaoRepositoy.findByNumeroCartao(numeroCartao);
    }
}
