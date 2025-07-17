package br.com.fiap.lanchonete.pagamento.device.persistence.repository;

import br.com.fiap.lanchonete.pagamento.commons.domain.PagamentoStatus;
import br.com.fiap.lanchonete.pagamento.device.persistence.entity.PagamentoEntity;
import java.util.Date;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoJpaRepository extends MongoRepository<PagamentoEntity, String> {
    @Query("{'pedidoId' : ?0}")
    @Update("{'$set': {'status': ?1, 'updateAt': ?2}}")
    void updateStatus(
            Long pedidoId,
            PagamentoStatus status,
            Date updatedAt);

    @Query("{'pedidoId' : ?0}")
    PagamentoEntity findByPedidoId(Long pedidoId);
}