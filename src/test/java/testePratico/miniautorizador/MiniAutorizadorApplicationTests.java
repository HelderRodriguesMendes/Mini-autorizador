package testePratico.miniautorizador;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import testePratico.miniautorizador.dto.CartaoDTO;
import testePratico.miniautorizador.dto.TransacaoDTO;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.exception.RegraNegocioException;
import testePratico.miniautorizador.model.Cartao;
import testePratico.miniautorizador.repository.CartaoRepositoy;
import testePratico.miniautorizador.service.CartaoService;
import testePratico.miniautorizador.service.TransacaoService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MiniAutorizadorApplicationTests {

	@InjectMocks
	private CartaoService cartaoService;

	@InjectMocks
	private TransacaoService transacaoService;

	@Mock
	private CartaoRepositoy cartaoRepositoy;

	private Cartao cartao;
	private Optional<Cartao> cartaoOptional;
	private CartaoDTO cartaoDTO;
	private TransacaoDTO transacaoDTO;

	private static final String NUMEROCARTAO = "6549873025632345";
	//private static final String NUMEROCARTAO_ERRADO = "6549873025634590";
	//private static final String CARTAO_INEXISTENTE = "CARTAO_INEXISTENTE";
	private static final Integer SENHA = 1234;
	//private static final Integer SENHA_ERRADA = 1234;
	//private static final String SENHA_INVALIDA = "SENHA_INVALIDA";
	private static final long ID = 1;
	private static final BigDecimal SALDO = BigDecimal.valueOf(100);
	private static final BigDecimal VALOR = BigDecimal.valueOf(300);
	//private static final BigDecimal VALOR_INCORRETO = BigDecimal.valueOf(700);
	//private static final String SALDO_INSUFICIENTE = "SALDO_INSUFICIENTE";
	private static final HttpStatus SALVO_SUCESSO = HttpStatus.CREATED;
	private static final HttpStatus ERRO_SALVAR_EXISTE = HttpStatus.UNPROCESSABLE_ENTITY;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		iniciarObjetos();
	}

	@Test
	void salvarCartaoComSucesso() {
		Mockito.when(cartaoRepositoy.save(Mockito.any())).thenReturn(cartao);
		RespostaDTO resposta = cartaoService.cadastrar(cartaoDTO);
		CartaoDTO cartaoDTO = (CartaoDTO) resposta.getBody();

		Assertions.assertNotNull(resposta);
		Assertions.assertEquals(RespostaDTO.class, resposta.getClass());
		Assertions.assertEquals(SALVO_SUCESSO, resposta.getHttpStatus());
		Assertions.assertEquals(NUMEROCARTAO, cartaoDTO.getNumeroCartao());
		Assertions.assertEquals(SENHA, cartaoDTO.getSenha());
	}

	@Test()
	void salvarCartaoJaExistente() {
		Mockito.when(cartaoService.BuscarCartao_NUMERO(Mockito.anyString())).thenReturn(cartaoOptional);
		try{
			cartaoService.cadastrar(cartaoDTO);
		}catch (RegraNegocioException ex){
			assertEquals(RegraNegocioException.class, ex.getClass());
			assertEquals(ERRO_SALVAR_EXISTE, ex.getRespostaDTO().getHttpStatus());
			assertEquals(cartaoDTO, ex.getRespostaDTO().getBody());
		}
	}

//	@Test
//	void realizarTransacao(){
//		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenReturn(cartaoOptional);
//		Mockito.when(cartaoRepositoy.findByNumeroCartaoAndSenha((Mockito.anyString()), (Mockito.anyInt()))).thenReturn(cartaoOptional);
//		Mockito.when(cartaoRepositoy.findByNumeroCartaoAndSaldoGreaterThanEqual((Mockito.anyString()), BigDecimal.valueOf((Mockito.anyInt())))).thenReturn(cartaoOptional);
//
//		RespostaDTO resposta = transacaoService.realizarTransacao(transacaoDTO, cartaoService);
//		String body = (String) resposta.getBody();
//
//		Assertions.assertEquals(RespostaDTO.class, resposta.getClass());
//		Assertions.assertEquals(SALVO_SUCESSO, resposta.getHttpStatus());
//		Assertions.assertEquals("OK", body);
//
//	}

	private void iniciarObjetos(){
		cartao = new Cartao(ID, NUMEROCARTAO, SENHA, SALDO);
		cartaoDTO = new CartaoDTO(SENHA, NUMEROCARTAO);
		cartaoOptional = Optional.of(new Cartao(ID, NUMEROCARTAO, SENHA, SALDO));
		transacaoDTO = new TransacaoDTO(NUMEROCARTAO, SENHA, VALOR);
	}
}
