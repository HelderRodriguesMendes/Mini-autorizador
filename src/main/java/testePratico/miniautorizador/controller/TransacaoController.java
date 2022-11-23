package testePratico.miniautorizador.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import testePratico.miniautorizador.dto.TransacaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.service.CartaoService;
import testePratico.miniautorizador.service.TransacaoService;

@Api(tags = "Transações")
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private CartaoService cartaoService;

    @ApiOperation(value = "Realizar Transacao")
    @PostMapping
    public ResponseEntity<?> realizarTransacao(@RequestBody TransacaoDTO transacaoDTO){
        RespostaDTO respostaDTO = transacaoService.realizarTransacao(transacaoDTO, cartaoService);
        return new ResponseEntity<>(respostaDTO.getBody(), respostaDTO.getHttpStatus());
    }
}
