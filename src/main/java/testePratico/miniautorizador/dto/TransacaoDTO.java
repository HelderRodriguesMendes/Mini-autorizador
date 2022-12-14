package testePratico.miniautorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {

    private String numeroCartao;
    private Integer senhaCartao;
    private BigDecimal valor;
}