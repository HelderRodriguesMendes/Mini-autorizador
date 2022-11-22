package testePratico.miniautorizador.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransacaoDTO {

    private String numeroCartao;
    private Integer senhaCartao;
    private BigDecimal valor;
}