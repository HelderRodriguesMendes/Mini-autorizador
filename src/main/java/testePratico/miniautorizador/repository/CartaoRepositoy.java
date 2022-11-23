package testePratico.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testePratico.miniautorizador.model.Cartao;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CartaoRepositoy extends JpaRepository<Cartao, Long> {
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
    Optional<Cartao> findByNumeroCartaoAndSenha(String numeroCartao, Integer senha);
    Optional<Cartao> findByNumeroCartaoAndSaldoGreaterThanEqual(String numeroCartao, BigDecimal saldo);
}