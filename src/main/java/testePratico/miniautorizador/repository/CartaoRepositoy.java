package testePratico.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testePratico.miniautorizador.model.Cartao;

@Repository
public interface CartaoRepositoy extends JpaRepository<Cartao, Long> {
    Cartao findByNumeroCartao(String numeroCartao);
}
