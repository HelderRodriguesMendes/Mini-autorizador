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
	private TransacaoDTO transacaoDTO_SENHA_INVALIDA;
	private TransacaoDTO transacaoDTO_CARTAO_INEXISTENTE;
	private TransacaoDTO transacaoDTO_SALDO_INSUFICIENTE;
	private RespostaDTO respostaDTO_ERRO_CONSULTAR_SALDO;
	private RespostaDTO respostaDTO_ERRO_CARTAO_INEXISTENTE;
	private RespostaDTO respostaDTO_ERRO_SENHA_INVALIDA;
	private RespostaDTO respostaDTO_ERRO_SALDO_INSUFICIENTE;

	private static final String NUMEROCARTAO = "6549873025632345";
	private static final String NUMEROCARTAO_ERRADO = "6559873025632835";
	private static final Integer SENHA = 1234;
	private static final Integer SENHA_ERRADA = 123444;
	private static final long ID = 1;
	private static final BigDecimal SALDO = BigDecimal.valueOf(500);
	private static final BigDecimal VALOR = BigDecimal.valueOf(100);
	private static final BigDecimal VALOR_INCORRETO = BigDecimal.valueOf(700);
	private static final String MENSAGEM_SALDO_INSUFICIENTE = "SALDO_INSUFICIENTE";
	private static final String MENSAGEM_CARTAO_INEXISTENTE = "CARTAO_INEXISTENTE";
	private static final String MENSAGEM_SENHA_INVALIDA = "SENHA_INVALIDA";
	private static final HttpStatus SALVO_SUCESSO = HttpStatus.CREATED;
	private static final HttpStatus STATUS_OK = HttpStatus.OK;
	private static final HttpStatus STATUS_NOT_FOUND = HttpStatus.NOT_FOUND;
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
		Assertions.assertEquals(SALVO_SUCESSO, resposta.getHttpStatus());
		Assertions.assertEquals(NUMEROCARTAO, cartaoDTO.getNumeroCartao());
		Assertions.assertEquals(SENHA, cartaoDTO.getSenha());
	}

	@Test()
	void salvarCartaoJaExistente() {
		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenReturn(cartaoOptional);
		try{
			cartaoService.cadastrar(cartaoDTO);
		}catch (RegraNegocioException ex) {
			CartaoDTO cartaoDTO = (CartaoDTO) ex.getRespostaDTO().getBody();

			assertEquals(RegraNegocioException.class, ex.getClass());
			assertEquals(ERRO_SALVAR_EXISTE, ex.getRespostaDTO().getHttpStatus());
			assertEquals(cartaoDTO, ex.getRespostaDTO().getBody());
			Assertions.assertEquals(NUMEROCARTAO, cartaoDTO.getNumeroCartao());
			Assertions.assertEquals(SENHA, cartaoDTO.getSenha());
		}
	}

	@Test
	void obterSaldoComSucesso(){
		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenReturn(cartaoOptional);

		RespostaDTO resposta = cartaoService.buscarSaldo(NUMEROCARTAO);
		String body = String.valueOf(resposta.getBody());
		BigDecimal saldo = BigDecimal.valueOf(Long.parseLong(body));

		Assertions.assertEquals(SALDO, saldo);
		Assertions.assertEquals(resposta.getHttpStatus(), STATUS_OK);
	}

	@Test
	void ErroAoObterSaldo(){
		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenThrow(new RegraNegocioException("", respostaDTO_ERRO_CONSULTAR_SALDO));

		try {
			cartaoService.buscarSaldo(NUMEROCARTAO_ERRADO);
		}catch (RegraNegocioException ex){
			assertEquals(RegraNegocioException.class, ex.getClass());
			assertEquals(respostaDTO_ERRO_CONSULTAR_SALDO, ex.getRespostaDTO());
			assertEquals(STATUS_NOT_FOUND, ex.getRespostaDTO().getHttpStatus());
			assertEquals(null, ex.getRespostaDTO().getBody());
		}
	}

	@Test
	void realizarTransacaoComSucesso(){
		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenReturn(cartaoOptional);
		Mockito.when(cartaoRepositoy.findByNumeroCartaoAndSenha((Mockito.anyString()), (Mockito.anyInt()))).thenReturn(cartaoOptional);
		Mockito.when(cartaoRepositoy.findByNumeroCartaoAndSaldoGreaterThanEqual((Mockito.anyString()), Mockito.any(BigDecimal.class))).thenReturn(cartaoOptional);

		RespostaDTO resposta = transacaoService.realizarTransacao(transacaoDTO, cartaoService);
		String body = (String) resposta.getBody();

		Assertions.assertEquals(SALVO_SUCESSO, resposta.getHttpStatus());
		Assertions.assertEquals("OK", body);

	}

	@Test
	void realizarTransacao_CARTAO_INVALIDO(){
		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenThrow(new RegraNegocioException("", respostaDTO_ERRO_CARTAO_INEXISTENTE));

		try {
			transacaoService.realizarTransacao(transacaoDTO_CARTAO_INEXISTENTE, cartaoService);
		} catch (RegraNegocioException ex){
			String body = String.valueOf(ex.getRespostaDTO().getBody());
			assertEquals(RegraNegocioException.class, ex.getClass());
			Assertions.assertEquals(ERRO_SALVAR_EXISTE, ex.getRespostaDTO().getHttpStatus());
			Assertions.assertEquals(MENSAGEM_CARTAO_INEXISTENTE, body);
		}
	}

	@Test
	void realizarTransacao_SENHA_INVALIDA(){
		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenReturn(cartaoOptional);
		Mockito.when(cartaoRepositoy.findByNumeroCartaoAndSenha((Mockito.anyString()), (Mockito.anyInt()))).thenThrow(new RegraNegocioException("", respostaDTO_ERRO_SENHA_INVALIDA));

		try {
			transacaoService.realizarTransacao(transacaoDTO_SENHA_INVALIDA, cartaoService);
		} catch (RegraNegocioException ex){
			String body = String.valueOf(ex.getRespostaDTO().getBody());
			assertEquals(RegraNegocioException.class, ex.getClass());
			Assertions.assertEquals(ERRO_SALVAR_EXISTE, ex.getRespostaDTO().getHttpStatus());
			Assertions.assertEquals(MENSAGEM_SENHA_INVALIDA, body);
		}
	}

	@Test
	void realizarTransacao_SALDO_INSUFICIENTE(){
		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenReturn(cartaoOptional);
		Mockito.when(cartaoRepositoy.findByNumeroCartaoAndSenha((Mockito.anyString()), (Mockito.anyInt()))).thenReturn(cartaoOptional);
		Mockito.when(cartaoRepositoy.findByNumeroCartaoAndSaldoGreaterThanEqual((Mockito.anyString()), Mockito.any(BigDecimal.class))).thenThrow(new RegraNegocioException("", respostaDTO_ERRO_SALDO_INSUFICIENTE));

		try {
			transacaoService.realizarTransacao(transacaoDTO_SALDO_INSUFICIENTE, cartaoService);
		} catch (RegraNegocioException ex){
			String body = String.valueOf(ex.getRespostaDTO().getBody());
			assertEquals(RegraNegocioException.class, ex.getClass());
			Assertions.assertEquals(ERRO_SALVAR_EXISTE, ex.getRespostaDTO().getHttpStatus());
			Assertions.assertEquals(MENSAGEM_SALDO_INSUFICIENTE, body);
		}
	}

	private void iniciarObjetos(){
		cartao = new Cartao(ID, NUMEROCARTAO, SENHA, SALDO);
		cartaoDTO = new CartaoDTO(SENHA, NUMEROCARTAO);
		cartaoOptional = Optional.of(new Cartao(ID, NUMEROCARTAO, SENHA, SALDO));
		transacaoDTO = new TransacaoDTO(NUMEROCARTAO, SENHA, VALOR);
		transacaoDTO_SENHA_INVALIDA = new TransacaoDTO(NUMEROCARTAO, SENHA_ERRADA, VALOR);
		transacaoDTO_CARTAO_INEXISTENTE = new TransacaoDTO(NUMEROCARTAO_ERRADO, SENHA, VALOR);
		transacaoDTO_SALDO_INSUFICIENTE = new TransacaoDTO(NUMEROCARTAO, SENHA, VALOR_INCORRETO);
		respostaDTO_ERRO_CONSULTAR_SALDO = new RespostaDTO(STATUS_NOT_FOUND, null);
		respostaDTO_ERRO_CARTAO_INEXISTENTE = new RespostaDTO(ERRO_SALVAR_EXISTE, MENSAGEM_CARTAO_INEXISTENTE);
		respostaDTO_ERRO_SENHA_INVALIDA = new RespostaDTO(ERRO_SALVAR_EXISTE, MENSAGEM_SENHA_INVALIDA);
		respostaDTO_ERRO_SALDO_INSUFICIENTE = new RespostaDTO(ERRO_SALVAR_EXISTE, MENSAGEM_SALDO_INSUFICIENTE);
	}
}
