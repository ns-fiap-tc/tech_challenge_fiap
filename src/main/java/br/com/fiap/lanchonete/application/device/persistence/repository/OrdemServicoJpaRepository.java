package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.entity.OrdemServicoEntity;
import br.com.fiap.lanchonete.business.core.domain.OrdemServicoStatus;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoJpaRepository extends JpaRepository<OrdemServicoEntity, Long> {
    List<OrdemServicoEntity> findByPedidoId(Long pedidoId);
    List<OrdemServicoEntity> findByPedidoItemId(Long pedidoItemId);

    @Query("FROM OrdemServicoEntity WHERE pedidoId = :pedidoId AND status <> :status")
    List<OrdemServicoEntity> findByPedidoIdStatus(
            @Param("pedidoId") Long pedidoId,
            @Param("status") OrdemServicoStatus status);

    @Modifying
    @Query("UPDATE OrdemServicoEntity SET status = :status, updatedAt = :updatedAt WHERE id = :id")
    void updateStatus(
            @Param("id") Long id,
            @Param("status") OrdemServicoStatus status,
            @Param("updatedAt") Date updatedAt);
}