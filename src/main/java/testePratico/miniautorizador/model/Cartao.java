package testePratico.miniautorizador.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import testePratico.miniautorizador.dto.CartaoDTO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(20)")
    private String numeroCartao;

    private Integer senha;

    @Setter
    private BigDecimal saldo;

    public Cartao(CartaoDTO cartaoDTO) {
        this.numeroCartao = cartaoDTO.getNumeroCartao();
        this.senha = cartaoDTO.getSenha();
        this.saldo = BigDecimal.valueOf(500);
    }
}
