package testePratico.miniautorizador.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import testePratico.miniautorizador.model.Cartao;

@Getter
@NoArgsConstructor
public class CartaoDTO {

    private String numeroCartao;

    private Integer senha;

    public CartaoDTO(Cartao cartao){
        this.numeroCartao = cartao.getNumeroCartao();
        this.senha = cartao.getSenha();
    }
}
