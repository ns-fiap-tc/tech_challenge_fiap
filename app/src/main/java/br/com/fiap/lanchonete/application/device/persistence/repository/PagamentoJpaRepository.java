package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.entity.PagamentoEntity;
import br.com.fiap.lanchonete.business.core.domain.PagamentoStatus;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoJpaRepository extends JpaRepository<PagamentoEntity, Long> {
    @Modifying
    @Query("UPDATE PagamentoEntity SET status = :status, updatedAt = :updatedAt WHERE id = :id")
    void updateStatus(
            @Param("id") Long id,
            @Param("status") PagamentoStatus status,
            @Param("updatedAt") Date updatedAt);

    @Query("FROM PagamentoEntity WHERE pedido.id = :pedidoId")
    PagamentoEntity findByPedidoId(@Param("pedidoId") Long pedidoId);
}