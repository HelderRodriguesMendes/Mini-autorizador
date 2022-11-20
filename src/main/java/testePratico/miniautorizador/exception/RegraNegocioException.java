package testePratico.miniautorizador.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import testePratico.miniautorizador.dto.resposta.RespostaDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegraNegocioException extends RuntimeException{

    private RespostaDTO respostaDTO;

    public RegraNegocioException(String message, RespostaDTO respostaDTO){
        super(message);
        this.respostaDTO = respostaDTO;
    }
}
