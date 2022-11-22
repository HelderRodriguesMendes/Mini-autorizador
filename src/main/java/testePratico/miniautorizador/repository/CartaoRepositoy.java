package testePratico.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import testePratico.miniautorizador.model.Cartao;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CartaoRepositoy extends JpaRepository<Cartao, Long> {
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
    Optional<Cartao> findByNumeroCartaoAndSenha(String numeroCartao, Integer senha);

    @Query(value = "SELECT * FROM cartao where numero_cartao = ?1 and saldo >= ?2", nativeQuery = true)
    Optional<Cartao> verificarSaldo(String numeroCartao, BigDecimal saldo);
}