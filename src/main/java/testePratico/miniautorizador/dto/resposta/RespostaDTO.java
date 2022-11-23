package testePratico.miniautorizador.dto.resposta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RespostaDTO {
    private HttpStatus httpStatus;
    private Object body;

    //DTO generico para padronizar os retornos aos controllers
    // de forma com que aceite os diferentes tipos de retornos de cada funcionalidade
}
