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
import testePratico.miniautorizador.dto.resposta.RespostaDTO;
import testePratico.miniautorizador.exception.RegraNegocioException;
import testePratico.miniautorizador.model.Cartao;
import testePratico.miniautorizador.repository.CartaoRepositoy;
import testePratico.miniautorizador.service.CartaoService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MiniAutorizadorApplicationTests {

	@InjectMocks
	private CartaoService cartaoService;

	@Mock
	private CartaoRepositoy cartaoRepositoy;

	private Cartao cartao;
	private Optional<Cartao> cartaoOptional;
	private CartaoDTO cartaoDTO;

	private static final String NUMEROCARTAO = "6549873025634504";
	private static final Integer SENHA = 1234;
	private static final long ID = 1;
	private static final BigDecimal SALDO = BigDecimal.valueOf(500);
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
		Mockito.when(cartaoRepositoy.findByNumeroCartao(Mockito.anyString())).thenReturn(cartaoOptional);
		try{
			cartaoService.cadastrar(cartaoDTO);
		}catch (RegraNegocioException ex){
			assertEquals(RegraNegocioException.class, ex.getClass());
			assertEquals(ERRO_SALVAR_EXISTE, ex.getRespostaDTO().getHttpStatus());
			assertEquals(cartaoDTO, ex.getRespostaDTO().getBody());
		}
	}

	private void iniciarObjetos(){
		cartao = new Cartao(ID, NUMEROCARTAO, SENHA, SALDO);
		cartaoDTO = new CartaoDTO(SENHA, NUMEROCARTAO);
		cartaoOptional = Optional.of(new Cartao(ID, NUMEROCARTAO, SENHA, SALDO));
	}
}
