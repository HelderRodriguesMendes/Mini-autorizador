package testePratico.miniautorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import testePratico.miniautorizador.model.Cartao;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartaoDTO {

    private Integer senha;
    private String numeroCartao;

    public CartaoDTO(Cartao cartao){
        this.numeroCartao = cartao.getNumeroCartao();
        this.senha = cartao.getSenha();
    }
}
