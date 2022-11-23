package testePratico.miniautorizador.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import testePratico.miniautorizador.dto.TransacaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.model.Cartao;
import testePratico.miniautorizador.utils.Validacao;
import testePratico.miniautorizador.utils.ValidarCartao;
import testePratico.miniautorizador.utils.ValidarSaldo;
import testePratico.miniautorizador.utils.ValidarSenha;

import java.math.BigDecimal;

@Service
public class TransacaoService {

    public RespostaDTO realizarTransacao(TransacaoDTO transacaoDTO, CartaoService cartaoService){
        this.avaliarTransacao(transacaoDTO, cartaoService);
        Cartao cartao = cartaoService.BuscarCartao_NUMERO(transacaoDTO.getNumeroCartao()).get();
        cartao.setSaldo(this.efetuarPagamento(cartao.getSaldo(), transacaoDTO.getValor()));
        cartaoService.salvarAlteracaoSaldo(cartao);
        return new RespostaDTO(HttpStatus.CREATED, "OK");
    }

                //INICIO DO FLUXO DO DESIGN PATTERNS STRATEGY
    private void avaliarTransacao(TransacaoDTO transacaoDTO, CartaoService cartaoService){
        Validacao cartao = new ValidarCartao();
        this.validarDadosTransacao(transacaoDTO, cartao, cartaoService);

        Validacao senha = new ValidarSenha();
        this.validarDadosTransacao(transacaoDTO, senha, cartaoService);

        Validacao saldo = new ValidarSaldo();
        this.validarDadosTransacao(transacaoDTO, saldo, cartaoService);
    }

    private void validarDadosTransacao(TransacaoDTO transacaoDTO, Validacao validacaoQualquer, CartaoService cartaoService){
        validacaoQualquer.validar(transacaoDTO, cartaoService);
    }

    private BigDecimal efetuarPagamento(BigDecimal saldo, BigDecimal valorTransacao){
        BigDecimal resultado = saldo.subtract(valorTransacao);
        return resultado;
    }
}