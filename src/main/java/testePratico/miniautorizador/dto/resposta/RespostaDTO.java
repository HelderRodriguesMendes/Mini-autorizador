package testePratico.miniautorizador.dto.resposta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class RespostaDTO {
    private HttpStatus httpStatus;
    private Object body;
}
