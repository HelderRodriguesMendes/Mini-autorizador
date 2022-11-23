package testePratico.miniautorizador.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testePratico.miniautorizador.dto.CartaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.service.CartaoService;

@Api(tags = "Cartões")
@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @ApiOperation(value = "Salvar um Novo Cartão")
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody CartaoDTO cartaoDTO){
        RespostaDTO respostaDTO = cartaoService.cadastrar(cartaoDTO);
        return new ResponseEntity<>(respostaDTO.getBody(), respostaDTO.getHttpStatus());
    }

    @ApiOperation(value = "Consultar Saldo do Cartão")
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<?> buscarSaldo(@PathVariable String numeroCartao){
        RespostaDTO respostaDTO = cartaoService.buscarSaldo(numeroCartao);
        return new ResponseEntity<>(respostaDTO.getBody(), respostaDTO.getHttpStatus());
    }
}
