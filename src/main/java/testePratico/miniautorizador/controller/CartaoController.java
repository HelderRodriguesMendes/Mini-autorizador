package testePratico.miniautorizador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import testePratico.miniautorizador.dto.CartaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.service.CartaoService;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody CartaoDTO cartaoDTO){
        RespostaDTO respostaDTO = cartaoService.cadastrar(cartaoDTO);
        return new ResponseEntity<>(respostaDTO.getBody(), respostaDTO.getHttpStatus());
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<?> buscarSaldo(@PathVariable String numeroCartao){
        RespostaDTO respostaDTO = cartaoService.buscarSaldo(numeroCartao);
        return new ResponseEntity<>(respostaDTO.getBody(), respostaDTO.getHttpStatus());
    }
}
