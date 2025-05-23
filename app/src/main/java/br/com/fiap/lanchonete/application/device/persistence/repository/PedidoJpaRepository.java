package br.com.fiap.lanchonete.application.device.persistence.repository;

import br.com.fiap.lanchonete.application.device.persistence.entity.PedidoEntity;
import br.com.fiap.lanchonete.business.core.domain.PedidoStatus;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, Long> {
    @Query( " SELECT p FROM PedidoEntity p "
           +" WHERE p.status != PedidoStatus.FINALIZADO "
           +" ORDER BY CASE WHEN p.status = PedidoStatus.PRONTO THEN 0 ELSE 1 END,"
           + "         CASE WHEN p.status = PedidoStatus.PREPARACAO THEN 0 ELSE 1 END, "
           + "         CASE WHEN p.status = PedidoStatus.RECEBIDO THEN 0 ELSE 1 END, "
           + "         p.createdAt ASC")
    List<PedidoEntity> findAllOrdered();
    List<PedidoEntity> findByClienteId(Long clienteId);
    List<PedidoEntity> findByStatusOrderByUpdatedAtDesc(PedidoStatus status);

    @Modifying
    @Query("UPDATE PedidoEntity SET status = :status, updatedAt = :updatedAt WHERE id = :id")
    void updateStatus(
            @Param("id") Long id,
            @Param("status") PedidoStatus status,
            @Param("updatedAt") Date updatedAt);
}