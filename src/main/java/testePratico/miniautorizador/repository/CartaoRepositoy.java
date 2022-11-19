package testePratico.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testePratico.miniautorizador.model.Cartao;

import java.util.Optional;

@Repository
public interface CartaoRepositoy extends JpaRepository<Cartao, Long> {
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
